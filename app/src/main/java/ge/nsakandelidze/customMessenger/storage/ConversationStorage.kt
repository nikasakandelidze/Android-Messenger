package ge.nsakandelidze.customMessenger.storage

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import ge.nsakandelidze.customMessenger.domain.Conversation
import ge.nsakandelidze.customMessenger.domain.User

class ConversationStorage {
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance();
    private val usersRef = database.getReference("conversations")

    fun fetchAllConversationsForUser(
        idOfUser: String,
        consumer: (MutableList<Conversation>) -> Unit
    ) {
        usersRef.addListenerForSingleValueEvent(object : ValueEventListener {
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
                consumer(toCollection)
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