package com.app.easy.travel.add.hotel

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.fragment.app.Fragment
import com.app.easy.travel.databinding.FragmentHotelInfoBinding
import com.app.easy.travel.helpers.hotel
import java.text.SimpleDateFormat
import java.util.Calendar


class HotelInfoFragment : Fragment(), TextWatcher, DatePickerDialog.OnDateSetListener {
    private var _binding: FragmentHotelInfoBinding? = null
    private val binding get() = _binding!!
    private val calender = Calendar.getInstance()
    private val formatDate = SimpleDateFormat("dd/MM/yyyy")
    private val formatTime = SimpleDateFormat("HH:mm")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHotelInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        setupViews()
        binding.edHotelDate.setOnClickListener {
            DatePickerDialog(
                requireActivity(),
                this,
                calender.get(Calendar.YEAR),
                calender.get(Calendar.MONTH),
                calender.get(Calendar.DAY_OF_MONTH)
            )
                .show()
        }
        binding.edHotelCheckIn.setOnClickListener {
            TimePickerDialog(
                requireActivity(),
                { view, hourOfDay, minute ->
                    calender.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    calender.set(Calendar.MINUTE, minute)
                    displayFormattedTime(calender.timeInMillis, "CheckIn")

                },
                calender.get(Calendar.HOUR_OF_DAY),
                calender.get(Calendar.MINUTE),
                true
            )
                .show()
        }
        binding.edHotelCheckOut.setOnClickListener {
            TimePickerDialog(
                requireActivity(),
                { view, hourOfDay, minute ->
                    calender.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    calender.set(Calendar.MINUTE, minute)
                    displayFormattedTime(calender.timeInMillis, "CheckOut")

                },
                calender.get(Calendar.HOUR_OF_DAY),
                calender.get(Calendar.MINUTE),
                true
            )
                .show()
        }

    }

    private fun displayFormattedTime(time: Long, check: String) {
        when (check) {
            "CheckIn" -> {
                binding.edHotelCheckIn.setText(formatTime.format(time))
            }

            "CheckOut" -> {
                binding.edHotelCheckOut.setText(formatTime.format(time))

            }

        }

    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        calender.set(year, month, dayOfMonth)
        displayFormattedDate(calender.timeInMillis)
    }

    private fun displayFormattedDate(time: Long) {
        binding.edHotelDate.setText(formatDate.format(time))
    }


    private fun setupViews() {

        _binding?.edHotelName?.setText(hotel.hotelName)
        _binding?.edHotelLocation?.setText(hotel.location)
        _binding?.edHotelDate?.setText(hotel.date)
        _binding?.edHotelCheckIn?.setText(hotel.checkIn)
        _binding?.edHotelCheckOut?.setText(hotel.checkOut)
        changedListener()


    }

    private fun changedListener() {
        _binding?.edHotelName?.addTextChangedListener(this)
        _binding?.edHotelLocation?.addTextChangedListener(this)
        _binding?.edHotelDate?.addTextChangedListener(this)
        _binding?.edHotelCheckIn?.addTextChangedListener(this)
        _binding?.edHotelCheckOut?.addTextChangedListener(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        hotel.hotelName = _binding?.edHotelName?.text.toString();
        hotel.location = _binding?.edHotelLocation?.text.toString();
        hotel.date = _binding?.edHotelDate?.text.toString();
        hotel.checkIn = _binding?.edHotelCheckIn?.text.toString();
        hotel.checkOut = _binding?.edHotelCheckOut?.text.toString();
    }

    override fun afterTextChanged(p0: Editable?) {
    }

}