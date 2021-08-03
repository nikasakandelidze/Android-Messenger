package ge.nsakandelidze.customMessenger.storage

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import ge.nsakandelidze.customMessenger.domain.Conversation
import ge.nsakandelidze.customMessenger.domain.Message

class ConversationStorage {
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance();
    private val convsRef = database.getReference("conversations")
    private val conversations = mutableListOf<Conversation>()

    fun fetchAllConversationsForUser(
        idOfUser: String,
        consumer: (MutableList<Conversation>) -> Unit
    ) {
        convsRef.addListenerForSingleValueEvent(object : ValueEventListener {
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
        getConversationDetailsForUsers(fromUserId, toUserId) {
            val messages = convsRef.child(it.getId()).child("messages")
            val push = messages.push()
            push.key?.let {
                messages.child(it).setValue(Message(message, "", fromUserId, toUserId))
                successCallback(Unit)
            }
        }

    }


    fun getConversationDetailsForUsers(
        loggedInUserId: String,
        otherUserId: String,
        consumer: (Conversation) -> Unit
    ) {
        convsRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
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
                    }.first()
                consumer(conversation)
            }

            override fun onCancelled(p0: DatabaseError) {
            }
        })
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