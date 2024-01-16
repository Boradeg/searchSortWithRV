package com.example.searchrv
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import java.util.Locale.filter

class DoctorAdapter(private var doctorList: MutableList<Doctor>) :
    RecyclerView.Adapter<DoctorAdapter.ViewHolder>() {

    private var filteredDoctorList: List<Doctor> = doctorList


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val doctorNameTextView: TextView = itemView.findViewById(R.id.doctorNameTextView)
        val dateTextView: TextView = itemView.findViewById(R.id.dateTextView)
        val usernameTextView: TextView = itemView.findViewById(R.id.usernameTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_doctor_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentDoctor = filteredDoctorList[position]
        holder.doctorNameTextView.text = currentDoctor.doctorName
        holder.dateTextView.text = currentDoctor.date
        holder.usernameTextView.text = currentDoctor.username
    }

    override fun getItemCount(): Int = filteredDoctorList.size
    fun updateDoctors(updatedDoctors: List<Doctor>) {
        doctorList = updatedDoctors.toMutableList()
        filter("") // Reset filter after sorting to display full sorted list
    }
    fun filter(query: String) {
         filteredDoctorList = if (query.isEmpty()) {
            doctorList.toList()
        } else {
            doctorList.filter { doctor ->
                doctor.doctorName.contains(query, ignoreCase = true) ||
                        doctor.username.contains(query, ignoreCase = true)
            }
        }
        notifyDataSetChanged()
    }

    fun filterDoctorList(query: String) {
        filteredDoctorList = if (query.isEmpty()) {
            doctorList
        } else {
            doctorList.filter {
                it.doctorName.contains(query, true)
            }
        }
        notifyDataSetChanged()
    }
}
