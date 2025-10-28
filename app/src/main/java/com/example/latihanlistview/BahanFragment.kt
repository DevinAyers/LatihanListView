package com.example.latihanlistview

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BahanFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BahanFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var listView: ListView
    private lateinit var btnTambah: Button
    private lateinit var adapter: ArrayAdapter<String>
    private var dataBahan = mutableListOf("Gula - Manis", "Garam - Asin", "Tepung - Pokok")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_bahan, container, false)

        listView = view.findViewById(R.id.listBahan)
        btnTambah = view.findViewById(R.id.btnTambahBahan)

        adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, dataBahan)
        listView.adapter = adapter

        btnTambah.setOnClickListener {
            showAddDialog()
        }

        listView.setOnItemClickListener { _, _, position, _ ->
            showEditDialog(position)
        }

        return view

    }

    private fun showAddDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Tambah Bahan")

        val layout = LinearLayout(requireContext())
        layout.orientation = LinearLayout.VERTICAL
        layout.setPadding(50, 40, 50, 10)

        val etNama = EditText(requireContext())
        etNama.hint = "Nama Bahan"
        val etKategori = EditText(requireContext())
        etKategori.hint = "Kategori Bahan"

        layout.addView(etNama)
        layout.addView(etKategori)
        builder.setView(layout)

        builder.setPositiveButton("Simpan") { _, _ ->
            val nama = etNama.text.toString().trim()
            val kategori = etKategori.text.toString().trim()
            if (nama.isNotEmpty() && kategori.isNotEmpty()) {
                dataBahan.add("$nama - $kategori")
                adapter.notifyDataSetChanged()
                Toast.makeText(requireContext(), "Bahan ditambahkan", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Isi semua kolom!", Toast.LENGTH_SHORT).show()
            }
        }

        builder.setNegativeButton("Batal") { dialog, _ -> dialog.dismiss() }
        builder.show()
    }

    private fun showEditDialog(position: Int) {
        val item = dataBahan[position]
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Edit / Hapus Bahan")
        builder.setMessage(item)

        builder.setPositiveButton("Ganti Kategori") { _, _ ->
            showUpdateDialog(position, item)
        }

        builder.setNegativeButton("Hapus") { _, _ ->
            dataBahan.removeAt(position)
            adapter.notifyDataSetChanged()
            Toast.makeText(requireContext(), "Bahan dihapus", Toast.LENGTH_SHORT).show()
        }

        builder.setNeutralButton("Batal") { dialog, _ -> dialog.dismiss() }
        builder.show()
    }

    private fun showUpdateDialog(position: Int, oldValue: String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Ganti Kategori")

        val layout = LinearLayout(requireContext())
        layout.orientation = LinearLayout.VERTICAL
        layout.setPadding(50, 40, 50, 10)

        val tvOld = TextView(requireContext())
        tvOld.text = "Data lama: $oldValue"
        tvOld.textSize = 16f

        val etNew = EditText(requireContext())
        etNew.hint = "Masukkan kategori baru"
        etNew.setText(oldValue.substringAfter("-").trim())

        layout.addView(tvOld)
        layout.addView(etNew)
        builder.setView(layout)

        builder.setPositiveButton("Simpan") { dialog, _ ->
            val newKategori = etNew.text.toString().trim()
            if (newKategori.isNotEmpty()) {
                val namaBahan = oldValue.substringBefore("-").trim()
                dataBahan[position] = "$namaBahan - $newKategori"
                adapter.notifyDataSetChanged()
                Toast.makeText(requireContext(), "Kategori diperbarui", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Tidak boleh kosong!", Toast.LENGTH_SHORT).show()
            }
            dialog.dismiss()
        }

        builder.setNegativeButton("Batal") { dialog, _ -> dialog.dismiss() }
        builder.show()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BahanFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BahanFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}