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
import com.app.easy.travel.databinding.FragmentArrivalBinding
import com.app.easy.travel.helpers.flight
import java.text.SimpleDateFormat
import java.util.Calendar


class ArrivalFragment : Fragment(), TextWatcher, DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {
    private var _binding: FragmentArrivalBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val calender = Calendar.getInstance()
    private val formatDate = SimpleDateFormat("dd/MM/yyyy")
    private val formatTime = SimpleDateFormat("HH:mm")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArrivalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        setupViews()
        binding.edArrivalDate.setOnClickListener {
            DatePickerDialog(
                requireActivity(),
                this,
                calender.get(Calendar.YEAR),
                calender.get(Calendar.MONTH),
                calender.get(Calendar.DAY_OF_MONTH)
            )
                .show()
        }

        binding.edArrivalTime.setOnClickListener {
            TimePickerDialog(
                requireActivity(),
                this,
                calender.get(Calendar.HOUR_OF_DAY),
                calender.get(Calendar.MINUTE),
                true
            ).show()

        }

        binding.edDepartureDate.setOnClickListener {
            DatePickerDialog(
                requireActivity(),
                { _, year, month, day ->
                    calender.set(year, month, day)
                    binding.edDepartureDate.setText(formatDate.format(calender.timeInMillis))
                },
                calender.get(Calendar.YEAR),
                calender.get(Calendar.MONTH),
                calender.get(Calendar.DAY_OF_MONTH)
            ).show()

        }
        binding.edDepartureTime.setOnClickListener {
            TimePickerDialog(
                requireActivity(),
                { _, hour, minute ->
                    calender.set(Calendar.HOUR_OF_DAY, hour)
                    calender.set(Calendar.MINUTE, minute)
                    binding.edDepartureTime.setText(formatTime.format(calender.timeInMillis))
                },
                calender.get(Calendar.HOUR_OF_DAY),
                calender.get(Calendar.MINUTE),
                true
            ).show()
        }

    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        calender.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calender.set(Calendar.MINUTE, minute)
        binding.edArrivalTime.setText(formatTime.format(calender.timeInMillis))
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        calender.set(year, month, dayOfMonth)
        binding.edArrivalDate.setText(formatDate.format(calender.timeInMillis))
    }

    private fun setupViews() {
        _binding?.edGate?.setText(flight.arrivalGate)
        _binding?.edFlightAirline?.setText(flight.arrivalAirline)
        _binding?.edFlightNumber?.setText(flight.arrivalFlightNumber)

        _binding!!.edArrivalTime.setText(flight.arrivalTime)
        _binding!!.edArrivalDate.setText(flight.arrivalDate)

        _binding?.edDestinationAirport?.setText(flight.arrival)
        _binding!!.edDepartureAirport.setText(flight.departure)
        _binding!!.edDepartureDate.setText(flight.departureDate)
        _binding!!.edDepartureTime.setText(flight.departureTime)
        changedListener()
    }

    private fun changedListener() {
        _binding?.edGate?.addTextChangedListener(this)
        _binding?.edFlightAirline?.addTextChangedListener(this)
        _binding?.edFlightNumber?.addTextChangedListener(this)

        _binding!!.edArrivalTime.addTextChangedListener(this)
        _binding!!.edArrivalDate.addTextChangedListener(this)

        _binding?.edDestinationAirport?.addTextChangedListener(this)
        _binding!!.edDepartureAirport.addTextChangedListener(this)
        _binding!!.edDepartureDate.addTextChangedListener(this)
        _binding!!.edDepartureTime.addTextChangedListener(this)


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        flight.arrivalGate = _binding?.edGate?.text.toString()
        flight.arrivalAirline = _binding?.edFlightAirline?.text.toString()
        flight.arrivalFlightNumber = _binding?.edFlightNumber?.text.toString()

        flight.arrival = _binding?.edDestinationAirport?.text.toString()
        flight.arrivalTime = _binding?.edArrivalTime?.text.toString()
        flight.arrivalDate = _binding?.edArrivalDate?.text.toString()

        flight.departure = _binding!!.edDepartureAirport.text.toString()
        flight.departureDate = _binding!!.edDepartureDate.text.toString()
        flight.departureTime = _binding!!.edDepartureTime.text.toString()


    }

    override fun afterTextChanged(p0: Editable?) {
    }
}