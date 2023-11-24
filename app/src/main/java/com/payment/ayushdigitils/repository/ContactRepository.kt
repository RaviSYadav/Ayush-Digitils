package com.payment.ayushdigitils.repository

import android.app.Application
import android.provider.ContactsContract
import com.payment.ayushdigitils.ui.fragments.prepaid.contact.ContactModel

class ContactRepository(private val application: Application) {


    suspend fun getContacts(): List<ContactModel> {
        val contacts = mutableListOf<ContactModel>()

        val contentResolver = application.contentResolver
        val cursor = contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null,
            null,
            null,
            null
        )

        cursor?.use { contactsCursor ->
            if (contactsCursor.moveToFirst()) {
                do {
                    val id = contactsCursor.getString(contactsCursor.getColumnIndex(ContactsContract.Contacts._ID))
                    val name = contactsCursor.getString(contactsCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                    val phoneCursor = contentResolver.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                        arrayOf(id),
                        null
                    )

                    phoneCursor?.use { phoneCursor ->
                        if (phoneCursor.moveToFirst()) {
                            val phoneNumber = phoneCursor.getString(
                                phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                            )
                            val contact = ContactModel(id, name, phoneNumber)
                            contacts.add(contact)
                        }
                    }
                } while (contactsCursor.moveToNext())
            }
        }

        cursor?.close()
        return contacts
    }
}
