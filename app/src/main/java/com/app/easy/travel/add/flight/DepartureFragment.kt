package com.app.easy.travel.add.flight

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.fragment.app.Fragment
import com.app.easy.travel.databinding.FragmentDepartureBinding
import com.app.easy.travel.helpers.flight
import java.text.SimpleDateFormat
import java.util.*

class DepartureFragment : Fragment(), TextWatcher, DatePickerDialog.OnDateSetListener
    ,TimePickerDialog.OnTimeSetListener{
    private var _binding: FragmentDepartureBinding? = null
    private val calender = Calendar.getInstance()
    private val formatDate = SimpleDateFormat("dd/MM/yyyy")
    private val formatTime = SimpleDateFormat("HH:mm")

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDepartureBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        setupViews()

        binding.edDepartureDate.setOnClickListener {
            DatePickerDialog(
                requireActivity(),
                this,
                calender.get(Calendar.YEAR),
                calender.get(Calendar.MONTH),
                calender.get(Calendar.DAY_OF_MONTH)
            )
                .show()
        }
        binding.edDepartureTime.setOnClickListener {
            TimePickerDialog(
                requireActivity(),
                this,
                calender.get(Calendar.HOUR_OF_DAY),
                calender.get(Calendar.MINUTE),
                true
            ).show()
        }
    }
    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        calender.set(Calendar.HOUR_OF_DAY,hourOfDay)
        calender.set(Calendar.MINUTE,minute)
        displayFormattedTime(calender.timeInMillis)
    }
    private fun displayFormattedTime(time: Long) {
        binding.edDepartureTime.text = formatTime.format(time)
    }

    private fun setupViews() {

        _binding?.edDepartureFrom?.setText(flight.departure)
        _binding?.edGate?.setText(flight.departureGate)
        _binding?.edFlightAirlineDeparture?.setText(flight.departureAirline)
        _binding?.edDepartureTime?.setText(flight.departureTime)
        _binding?.edDepartureDate?.setText(flight.departureDate)
        _binding?.edFlightNumberDeparture?.setText(flight.departureFlightNumber)
        changedListener()


    }
    private fun changedListener() {
        _binding?.edDepartureFrom?.addTextChangedListener(this)
        _binding?.edGate?.addTextChangedListener(this)
        _binding?.edFlightAirlineDeparture?.addTextChangedListener(this)
        _binding?.edDepartureTime?.addTextChangedListener(this)
        _binding?.edDepartureDate?.addTextChangedListener(this)
        _binding?.edFlightNumberDeparture?.addTextChangedListener(this)
    }
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        calender.set(year, month, dayOfMonth)
        displayFormattedDate(calender.timeInMillis)
    }

    private fun displayFormattedDate(time:Long) {
        binding.edDepartureDate.text = formatDate.format(time)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        flight.departure = _binding?.edDepartureFrom?.text.toString();
        flight.departureGate = _binding?.edGate?.text.toString();
        flight.departureAirline = _binding?.edFlightAirlineDeparture?.text.toString();
        flight.departureTime = _binding?.edDepartureTime?.text.toString();
        flight.departureDate = _binding?.edDepartureDate?.text.toString();
        flight.departureFlightNumber = _binding?.edFlightNumberDeparture?.text.toString();

    }

    override fun afterTextChanged(p0: Editable?) {
    }
}