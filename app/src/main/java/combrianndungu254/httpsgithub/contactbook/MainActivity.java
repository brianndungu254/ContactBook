package combrianndungu254.httpsgithub.contactbook;

import android.content.Context;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public EditText mNameInput;
    public EditText mPhoneInput;
    public EditText mEmailInput;

    private static int numContactsAdded;

    private TextView mErrorMessage;
    private TextView mSortedList;

    private EditText mContactToAdd;
    private EditText mErrorMesssageDisplay;
    private EditText mSortingDisplay;

    static Contact[] contactsArray;




    @Override
    /**
     * onCreate is the method that is called when the activity is created
     * the first time
     *
     * @param savedInstanceState is a Bundle of information that is used
     *                           to restore this activity to a previous state
     * @return Nothing is returned
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int resourceid;

        mErrorMessage = (TextView) findViewById(R.id.tv_error_message);
        mSortedList = (TextView) findViewById(R.id.tv_sort_contacts);

        mNameInput = (EditText) findViewById(R.id.et_name);
        mPhoneInput = (EditText) findViewById(R.id.et_phone);
        mEmailInput = (EditText) findViewById(R.id.et_email);


        contactsArray = new Contact[50];
        numContactsAdded = 0;


    }


    public void addContact(View view)
    {
        InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        Contact toAdd;


        if( mNameInput.getText().length() == 0)
        {
            mErrorMesssageDisplay.setText("you have not added any contact.. please add one ");
        }
        else if ( numContactsAdded >= contactsArray.length )
        {
            mErrorMessage.setText( "you have added the MAXIMUM amount of contacts");
        }

        else
        {
            toAdd = new Contact( mNameInput.getText().toString(), mPhoneInput.getText().toString(), mEmailInput.getText().toString());

            contactsArray[ numContactsAdded ] = toAdd ;
            numContactsAdded++;
            mNameInput.setText("");
            mPhoneInput.setText("");
            mEmailInput.setText("");

            mNameInput.requestFocus();

            if( inputManager != null)
            {
                inputManager.showSoftInput(mContactToAdd, InputMethodManager.SHOW_IMPLICIT );
            }
        }
        mErrorMessage.setText("contact added successfully");





    }

    public void sortContacts(View view)
    {
        String sortedContacts = "";

        //insertionSort();
        //selectionSort();
        quickSort(contactsArray,0, numContactsAdded -1);


        for(int i = 0; i < numContactsAdded; i++)
        {
            if(contactsArray[i] !=null)
            {
                sortedContacts +=String.format( "Name: %20s\nPhone: %20s\nEmail: %20s\n\n", contactsArray[i].getName(), contactsArray[i].getPhone(), contactsArray[i].getEmail());
            }
        }

        mErrorMessage.setText("");

        mSortedList.setText(sortedContacts);

    }

    /**
     * insertionSort uses the Insertion Sort algorithm to sort a list of items
     * in ascending order
     *
     * @param "" There are no parameters
     * @return a String that displays the sorted list and how many steps it took
     */
    private void insertionSort()
    {
        //key might need to be a different data type
        Contact key;
        int index;

        //this is where insertion sort starts
        for( int j = 0; j < numContactsAdded; j++ )
        {
            key = contactsArray[j];
            index = j - 1;

            while (index >= 0 && contactsArray[index].getName().compareTo(key.getName()) > 0)
            {
                contactsArray[index + 1] = contactsArray[index];
                index = index - 1;

            }
            contactsArray[index + 1] = key;

        }
    }




    private void selectionSort()
    {

        int minIndex;
        Contact toSwap;

        for (int j = 0; j < numContactsAdded - 1; j++)
        {
            minIndex = j;

            for (int k = j + 1; k < numContactsAdded; k++)
            {
                if (contactsArray[k].getName().compareTo(contactsArray[minIndex].getName()) < 0)
                {
                    minIndex = k;
                }

            }

            toSwap = contactsArray[minIndex];
            contactsArray[minIndex] = contactsArray[j];
            contactsArray[j] = toSwap;
        }
    }


    /**
     * quickSort uses the Quick Sort algorithm to sort a list in
     * ascending order
     *
     * @param array is the array we are sorting
     * @param low is the beginning index of the section of the array we would like
     *            to sort
     * @param high is the ending index of the section of the array we would like
     *             to sort
     * @return Nothing is returned
     */
    private void quickSort( Contact[] array, int low, int high )
    {
        int middle;
        Contact pivot;

        int i;
        int j;

        Contact toSwap;

        if( low < high )
        {
            middle = low + (high - low) / 2;
            pivot = array[middle];

            i = low;
            j = high;

            while( i <= j )
            {
                while( array[i].getName().compareTo(pivot.getName()) < 0 )
                {
                    i++;


                }

                while( array[j].getName().compareTo(pivot.getName()) > 0 )
                {
                    j--;


                }

                if( i <= j )
                {
                    toSwap = array[i];
                    array[i] = array[j];
                    array[j] = toSwap;
                    i++;
                    j--;
                }


            }

            if( low < j )
            {
                quickSort( array, low, j );


            }

            if( high > i )
            {
                quickSort( array, i, high );


            }
        }

    }
}
