package com.example.searchrv

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlin.math.min
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.appcompat.widget.SearchView
import java.text.SimpleDateFormat
import java.util.*

import android.view.LayoutInflater
import android.view.ViewGroup

import java.util.Locale.filter

class MainActivity : AppCompatActivity() {
    private lateinit var doctorAdapter: DoctorAdapter
    private lateinit var doctorRecyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var sortBtn: Button
    private lateinit var pickDateBtn: Button
    private lateinit var allBtn: Button
    private lateinit var noAppoinment: TextView
    private lateinit var doctorDataList: MutableList<Doctor>

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Assuming doctorDataList is a list of Doctor objects
        doctorDataList = getDoctorData()

        doctorRecyclerView = findViewById(R.id.doctorRecyclerView)
        noAppoinment = findViewById(R.id.noAppoinmentText)
        searchView = findViewById(R.id.searchvieww)
        sortBtn = findViewById(R.id.sortingBtn)
        pickDateBtn = findViewById(R.id.datePicker)
        allBtn = findViewById(R.id.allAppintment)
        val layoutManager = LinearLayoutManager(this)

        //doctorAdapter = DoctorAdapter(doctorDataList)
        doctorAdapter = DoctorAdapter(doctorDataList)
        doctorRecyclerView.adapter = doctorAdapter
        doctorRecyclerView.layoutManager = LinearLayoutManager(this)


        val sortedDoctors = doctorDataList.sortedBy { parseDate(it.date) }
        sortBtn.setOnClickListener {
            noAppoinment.visibility=View.GONE
            doctorAdapter.updateDoctors(sortedDoctors)
        }
        allBtn.setOnClickListener {
            noAppoinment.visibility=View.GONE
            doctorAdapter.updateDoctors(getDoctorData())
        }

        pickDateBtn.setOnClickListener {
            noAppoinment.visibility=View.GONE

            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                { _, year, monthOfYear, dayOfMonth ->
                    val selectedDate = "%02d/%02d/%04d".format(dayOfMonth, monthOfYear + 1, year)
                    Toast.makeText(this, selectedDate, Toast.LENGTH_SHORT).show()
                    searchDoctorsByDate(selectedDate)
                },
                year,
                month,
                day
            )
            datePickerDialog.show()
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                //Toast.makeText(this@MainActivity, "onQueryTextChange", Toast.LENGTH_SHORT).show()
                doctorAdapter.filterDoctorList(newText.orEmpty())
                return true
            }
        })
    }
    fun searchDoctorsByDate(selectedDate: String) {
        val doctorsOnSelectedDate = doctorDataList.filter { doctor ->
            doctor.date == selectedDate // Assuming doctor.date is in dd/mm/yyyy format
        }

        if (doctorsOnSelectedDate.isEmpty()) {
            doctorAdapter.updateDoctors(emptyList())
            noAppoinment.visibility=View.VISIBLE
            noAppoinment.text="No appointments on $selectedDate"
            Toast.makeText(this, "No appointments on $selectedDate", Toast.LENGTH_SHORT).show()
        } else {
            // Update RecyclerView with filtered data
            doctorAdapter.updateDoctors(doctorsOnSelectedDate)
        }
    }

    fun parseDate(dateString: String): Date {
        val formats = arrayOf("dd/MM/yyyy", "dd/MM/yy") // Add more formats if needed
        var parsedDate: Date? = null

        for (format in formats) {
            try {
                val sdf = SimpleDateFormat(format, Locale.getDefault())
                sdf.isLenient = false
                parsedDate = sdf.parse(dateString)
                if (parsedDate != null) {
                    break
                }
            } catch (e: Exception) {
                // Handle parsing exceptions or move to the next format
            }
        }

        requireNotNull(parsedDate) { "Date parsing failed for $dateString" }
        return parsedDate
    }

    private fun getDoctorData(): MutableList<Doctor> {
        // Replace this with your logic to fetch doctor data
        // Sample data for demonstration
        return mutableListOf(
            Doctor("Dr. David Johnson", "03/03/2024", "user3"),
            Doctor("Dr. John Doe", "01/01/2023", "user1"),
            Doctor("Dr. Emily Smith", "02/02/2022", "user2"),

            Doctor("Dr. John Doe", "01/01/2000", "user1"),
            Doctor("Dr. gokul Smith", "02/01/2001", "user2"),
            Doctor("Dr. sagar Johnson", "03/01/2002", "user3"),
            Doctor("Dr. shinde Doe", "04/01/2003", "user4"),
            Doctor("Dr. rahul Smith", "05/02/2004", "user5"),
            Doctor("Dr. David Johnson", "03/03/2005", "user6"),
            Doctor("Dr. siya Doe", "01/01/2006", "user7"),
            Doctor("Dr. DJ Smith", "02/03/2007", "user8"),
            Doctor("Dr. berlin Johnson", "03/03/2008", "user9"),
            Doctor("Dr. tokyo Doe", "01/05/2023", "user10"),
            Doctor("Dr. Emily Smith", "22/02/2023", "user12"),
            Doctor("Dr. David Johnson", "03/04/2023", "user13"),

            Doctor("Dr. David Johnson", "03/03/2024", "user3"),
            Doctor("Dr. John Doe", "01/01/2023", "user1"),
            Doctor("Dr. Emily Smith", "02/02/2022", "user2"),

            Doctor("Dr. John Doe", "01/01/2000", "user1"),
            Doctor("Dr. gokul Smith", "02/01/2001", "user2"),
            Doctor("Dr. sagar Johnson", "03/01/2002", "user3"),
            Doctor("Dr. shinde Doe", "04/01/2003", "user4"),
            Doctor("Dr. rahul Smith", "05/02/2004", "user5"),
            Doctor("Dr. David Johnson", "03/03/2005", "user6"),
            Doctor("Dr. siya Doe", "01/01/2006", "user7"),
            Doctor("Dr. DJ Smith", "02/03/2007", "user8"),
            Doctor("Dr. berlin Johnson", "03/03/2008", "user9"),
            Doctor("Dr. tokyo Doe", "01/05/2023", "user10"),
            Doctor("Dr. Emily Smith", "22/02/2023", "user12"),
            Doctor("Dr. David Johnson", "03/04/2023", "user13"),

            Doctor("Dr. David Johnson", "03/03/2024", "user3"),
            Doctor("Dr. John Doe", "01/01/2023", "user1"),
            Doctor("Dr. Emily Smith", "02/02/2022", "user2"),

            Doctor("Dr. John Doe", "01/01/2000", "user1"),
            Doctor("Dr. gokul Smith", "02/01/2001", "user2"),
            Doctor("Dr. sagar Johnson", "03/01/2002", "user3"),
            Doctor("Dr. shinde Doe", "04/01/2003", "user4"),
            Doctor("Dr. rahul Smith", "05/02/2004", "user5"),
            Doctor("Dr. David Johnson", "03/03/2005", "user6"),
            Doctor("Dr. siya Doe", "01/01/2006", "user7"),
            Doctor("Dr. DJ Smith", "02/03/2007", "user8"),
            Doctor("Dr. berlin Johnson", "03/03/2008", "user9"),
            Doctor("Dr. tokyo Doe", "01/05/2023", "user10"),
            Doctor("Dr. Emily Smith", "22/02/2023", "user12"),
            Doctor("Dr. David Johnson", "03/04/2023", "user13"),

            // Add more Doctor objects as needed
        )
    }
}
