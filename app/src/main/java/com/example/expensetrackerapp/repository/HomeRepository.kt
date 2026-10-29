package com.example.expensetrackerapp.repository

import com.example.expensetrackerapp.model.Expenses
import com.example.expensetrackerapp.utils.HomeState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import javax.inject.Inject

class HomeRepository @Inject constructor(private val auth: FirebaseAuth,private val db: FirebaseFirestore) {


     fun getExpense(onResult:(HomeState) -> Unit): ListenerRegistration?
        {
        val userId = auth.currentUser?.uid
        if (userId==null)
        {
            onResult(HomeState.HomeFailure("User not Logged in"))
            return null
        }


            return db.collection("Expenses")
                .whereEqualTo("userId",userId)
                .addSnapshotListener { snapShot,error ->

                    if (error!=null)
                    {
                        onResult(HomeState.HomeFailure(
                            error.localizedMessage?: "There is a problem"
                        ))

                        return@addSnapshotListener
                    }

                    val expenseList = snapShot?.documents?.mapNotNull { result->
                        result.toObject(Expenses::class.java)
                    }?:emptyList()

                    onResult(HomeState.HomeSuccess(expenseList))

                }
    }

    fun getName(onResult:(String) -> Unit)
    {
        val userId = auth.currentUser?.uid

        if (userId == null)
        {
            onResult("User")
            return
        }


        db.collection("users")
            .document(userId)
            .get()
            .addOnSuccessListener { document->
               val name = document.getString("name")?:"User"
                onResult(name)
            }
            .addOnFailureListener {
                onResult("User")
            }
    }




}