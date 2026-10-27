package com.example.expensetrackerapp.repository

import com.example.expensetrackerapp.model.Expenses
import com.example.expensetrackerapp.utils.HomeState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.math.exp

class HomeRepository @Inject constructor(private val auth: FirebaseAuth,private val db: FirebaseFirestore) {


    suspend fun getExpense(): HomeState
    {
        val userId = auth.currentUser?.uid ?: return HomeState.HomeFailure("User not logged In")

       return try {
           val snapshot = db.collection("Expenses")
               .whereEqualTo("userId",userId)
               .get().await()

           val expenseList = snapshot.documents.mapNotNull { documentSnapshot ->
               documentSnapshot.toObject(Expenses::class.java)
           }

           HomeState.HomeSuccess(expenseList)
       }catch (e: Exception)
        {
            HomeState.HomeFailure(e.localizedMessage ?: "There is a problem")
        }
    }
}