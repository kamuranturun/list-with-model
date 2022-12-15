package com.kamuran.myapp


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {
    private lateinit var addTodo: Button //ekleme butonu
    private lateinit var todoName: EditText //veri girilecek olan edittext
    private lateinit var todoList: RecyclerView //recyclerviewimiz liste halinde
    private lateinit var mTodoListAdapter: ToDoListAdapter //adaptör klasımız
    private var modelToBeUpdated: Stack<ToDoModel> = Stack()
    // hafızadaki stak kısmı içine dataclasımız olan
    //modeli alır güncelleme değişiklikleri burda tutulur...
    private val monTodoClickListener = object : ToDoListAdapter.OnTodoClickListener {
        //monTodoClickListener nesnesi object olarak tanımladık adaptör classını alarak içine
        //OnTodoClickListener interfacesini alacak
        override fun onUpdate(position: Int, model: ToDoModel) {

            modelToBeUpdated.add(model)//stack


// düzenleme metninde tıklanan öğenin değerini ayarla
            todoName.setText(model.name)



            butonTextiDegistir()

        }



        override fun onDelete(model: ToDoModel) {

            // just remove the item from list
            mTodoListAdapter.removeTodo(model)
        }
    }

    private fun butonTextiDegistir() {
        if (true)
            startTimer()
        else
            stopTimer()
    }

    private fun startTimer() { //guncelle
        // we have nothing to update
        addTodo.setText("guncelle")
        addTodo.setOnClickListener {
            if (modelToBeUpdated.isEmpty()) return@setOnClickListener

            val name = todoName.text.toString()
            if (!name.isBlank()) {
                val model = modelToBeUpdated.pop()
                model.name = name

                mTodoListAdapter.updateTodo(model)

                // reset the input
                todoName.setText("")
                addTodo.setText("Add")
                eklegelsin()


            }
        }
    }

    private fun eklegelsin() {
        addTodo.setOnClickListener {

            val name = todoName.text.toString()

            if (!name.isBlank()) {

                // prepare id on incremental basis
                val id = mTodoListAdapter.getNextItemId()

                // prepare model for use
                val model = ToDoModel(id, name)

                // add model to the adapter
                mTodoListAdapter.addTodo(model)
                mTodoListAdapter.notifyDataSetChanged();

                // reset the input
                todoName.setText("")

            }
        }
    }

    private fun stopTimer() { //ekleme
        addTodo.setOnClickListener {

            val name = todoName.text.toString()

            if (!name.isBlank()) {

                // prepare id on incremental basis
                val id = mTodoListAdapter.getNextItemId()

                // prepare model for use
                val model = ToDoModel(id, name)

                // add model to the adapter
                mTodoListAdapter.addTodo(model)

                // reset the input
                todoName.setText("")

            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        todoList = findViewById(R.id.recyclerView)
        todoList.layoutManager = LinearLayoutManager(this)
        todoList.setHasFixedSize(true)

        mTodoListAdapter = ToDoListAdapter(this, monTodoClickListener)
        todoList.adapter = mTodoListAdapter




        todoName = findViewById(R.id.editText1)




        addTodo = findViewById(R.id.butonEkle)

        addTodo.setOnClickListener {

            val name = todoName.text.toString()

            if (!name.isBlank()) {

                // prepare id on incremental basis
                val id = mTodoListAdapter.getNextItemId()

                // prepare model for use
                val model = ToDoModel(id, name)

                // add model to the adapter
                mTodoListAdapter.addTodo(model)

                // reset the input
                todoName.setText("")

            }
        }


    }
}