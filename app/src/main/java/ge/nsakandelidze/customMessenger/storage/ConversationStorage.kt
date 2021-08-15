package ge.nsakandelidze.customMessenger.storage

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import ge.nsakandelidze.customMessenger.domain.Conversation
import ge.nsakandelidze.customMessenger.domain.Message
import java.time.LocalDateTime

class ConversationStorage {
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance();
    private val convsRef = database.getReference("conversations")
    private val conversations = mutableListOf<Conversation>()

    fun fetchAllConversationsForUser(
        idOfUser: String,
        consumer: (MutableList<Conversation>) -> Unit
    ) {
        convsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                val children = p0.children
                val toCollection = children
                    .filter {
                        val value = it.getValue(Conversation::class.java)
                        value?.from_student_id.equals(idOfUser)
                                || value?.to_student_id.equals(idOfUser)
                    }.mapNotNull {
                        val value = it.getValue(Conversation::class.java)
                        value
                    }.toCollection(mutableListOf())
                conversations.addAll(toCollection)
                consumer(toCollection)
            }

            override fun onCancelled(p0: DatabaseError) {
            }
        })
    }

    fun addNewMessageIntoConversation(
        fromUserId: String,
        toUserId: String,
        message: String,
        successCallback: (Unit) -> Unit
    ) {
        getSingleConversationDetailsForUsers(fromUserId, toUserId) {
            if (it == null) {
                val key = convsRef.push().key
                key?.let {
                    convsRef.child(key).setValue(Conversation(fromUserId, toUserId, mapOf()))
                    val childComponent = convsRef.child(key).child("messages")
                    childComponent.push().key?.let {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            childComponent.child(it)
                                .setValue(Message(message, LocalDateTime.now().toString(), fromUserId, toUserId))
                        } else {
                            TODO("VERSION.SDK_INT < O")
                        }
                    }

                }
            } else {
                val messages = it?.let { it1 -> convsRef.child(it1.getId().orEmpty()).child("messages") }
                val push = messages?.push()
                push?.key?.let {
                    messages.child(it).setValue(Message(message, LocalDateTime.now().toString(), fromUserId, toUserId))
                    successCallback(Unit)
                }
            }
        }

    }


    fun getConversationDetailsForUsers(
        loggedInUserId: String,
        otherUserId: String,
        consumer: (Conversation?) -> Unit
    ) {
        convsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                processConversations(p0, loggedInUserId, otherUserId, consumer)
            }

            override fun onCancelled(p0: DatabaseError) {
            }
        })
    }

    fun getSingleConversationDetailsForUsers(
        loggedInUserId: String,
        otherUserId: String,
        consumer: (Conversation?) -> Unit
    ) {
        convsRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                processConversations(p0, loggedInUserId, otherUserId, consumer)
            }

            override fun onCancelled(p0: DatabaseError) {
            }
        })
    }

    private fun processConversations(
        p0: DataSnapshot,
        loggedInUserId: String,
        otherUserId: String,
        consumer: (Conversation?) -> Unit
    ) {
        val children = p0.children
        val conversation = children
            .filter {
                val value = it.getValue(Conversation::class.java)
                (value?.from_student_id.equals(loggedInUserId) && value?.to_student_id.equals(
                    otherUserId
                ))
                        || (value?.from_student_id.equals(otherUserId) && value?.to_student_id.equals(
                    loggedInUserId
                ))
            }.mapNotNull {
                val value = it.getValue(Conversation::class.java)
                value?.setId(it.key!!)
                value
            }.firstOrNull()
        var messages = conversation?.messages
        consumer(conversation)
    }

    companion object {
        private lateinit var INSTANCE: ConversationStorage

        fun createDb() {
            INSTANCE = ConversationStorage()
        }

        fun getInstance(): ConversationStorage {
            return INSTANCE
        }
    }
}