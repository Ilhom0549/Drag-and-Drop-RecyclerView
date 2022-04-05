package uz.ilkhomkhuja.draganddroprecyclerview.helpers

import android.content.res.Resources
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import uz.ilkhomkhuja.draganddroprecyclerview.viewmodels.MainViewModel


class CustomItemTouchHelper(
    private var mainViewModel: MainViewModel,
    private var resources: Resources
) : ItemTouchHelper.Callback() {

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
        return makeMovementFlags(dragFlags, swipeFlags)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        mainViewModel.move(
            fromPosition = viewHolder.adapterPosition,
            toPosition = target.adapterPosition
        )
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        mainViewModel.dismiss(viewHolder.adapterPosition)
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        super.onSelectedChanged(viewHolder, actionState)
        val view = viewHolder?.itemView ?: return
        when (actionState) {
            ItemTouchHelper.ACTION_STATE_DRAG -> {
                ViewCompat.animate(view)
                    .setDuration(150L)
                    .alpha(0.5f)
                    .scaleX(0.9f)
                    .scaleY(0.9f)
                    .translationZ(resources.getDimension(com.intuit.sdp.R.dimen._5sdp))

            }
            ItemTouchHelper.ACTION_STATE_SWIPE -> {
                ViewCompat.animate(view)
                    .setDuration(150L)
                    .alpha(1f)
                    .scaleX(1f)
                    .scaleY(0.9f)
                    .translationZ(resources.getDimension(com.intuit.sdp.R.dimen._5sdp))
            }
        }
    }

    override fun clearView(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ) {
        super.clearView(recyclerView, viewHolder)
        ViewCompat.animate(viewHolder.itemView)
            .setDuration(150L)
            .alpha(1f)
            .scaleX(1f)
            .scaleY(1f)
            .translationZ(0f)
    }

    override fun isLongPressDragEnabled(): Boolean {
        return false
    }

    override fun isItemViewSwipeEnabled(): Boolean {
        return true
    }
}