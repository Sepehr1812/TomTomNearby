package ir.divar.interviewtask.util

import android.content.Context
import androidx.appcompat.app.AlertDialog
import ir.divar.interviewtask.R


/**
 * Helps to generalize AlertDialog creating and showing.
 *
 * @author Sepi 6/9/22
 */
object DialogHelper {

    inline fun showInfoMessage(
        context: Context,
        message: String,
        positiveButtonText: String,
        crossinline action: () -> Unit
    ) {
        AlertDialog.Builder(context, R.style.AlertDialogCustom).apply {
            setMessage(message)
            setPositiveButton(positiveButtonText) { _, _ -> action() }
            setCancelable(false)
        }.create().show()
    }
}