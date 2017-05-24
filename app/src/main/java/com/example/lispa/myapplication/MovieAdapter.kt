package com.example.lispa.myapplication

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.support.transition.Scene
import android.support.transition.TransitionManager
import android.support.transition.ChangeBounds
import android.support.transition.Fade
import android.support.transition.TransitionSet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.lispa.myapplication.model.Movie
import kotlinx.android.synthetic.main.list_item.view.*
import kotlin.collections.mutableListOf

/**
 * Created by lispa on 22/05/2017.
 */


class MovieAdapter(var movies: MutableList<Movie>,var context: Context) : RecyclerView.Adapter<MovieAdapter.MovieHolder>() {

    private var mTransitionManager: TransitionManager? = null
    private var mExpandedScene: Scene? = null
    private var mCollapsedScene: Scene? = null
    private var mCurrentScene: Scene? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val inflater = LayoutInflater.from(parent!!.context)
        val view = inflater.inflate(R.layout.list_item, parent, false)
        return MovieHolder(view)
    }

    override fun onBindViewHolder(holder: MovieHolder?, position: Int) {
        holder!!.bind(movies.get(position))
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    inner class MovieHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        fun bind(movie: Movie) = with(itemView) {
            titleMovie.text = movie.title

            itemView.setOnClickListener {
                Toast.makeText(context, movie.title, Toast.LENGTH_SHORT).show()

                setupTransitions(itemView, movie)

                if (mCurrentScene == mExpandedScene) {
                    mCurrentScene = mCollapsedScene
                } else {
                    mCurrentScene = mExpandedScene
                }

                mTransitionManager!!.transitionTo(mCurrentScene!!)
                titleMovie.text = movie.title
            }
        }

        fun setupTransitions(view :View, movie: Movie) {
            mTransitionManager = TransitionManager()
            var transitionRoot :ViewGroup =  view.rootDetail

            mExpandedScene = Scene.getSceneForLayout(transitionRoot, R.layout.list_item_expanded, context)

            mExpandedScene?.setEnterAction { Runnable {
                transitionRoot.titleMovie.text = movie.title
                mCurrentScene = mExpandedScene
            } }

            val expandTransitionSet = TransitionSet()
            expandTransitionSet.ordering = TransitionSet.ORDERING_SEQUENTIAL
            val changeBounds = ChangeBounds()
            changeBounds.duration = 200
            expandTransitionSet.addTransition(changeBounds)

            mCollapsedScene = Scene.getSceneForLayout(transitionRoot,R.layout.list_item, context)

            mCollapsedScene?.setEnterAction {Runnable {
                transitionRoot.titleMovie.text = movie.title
                mCurrentScene = mCollapsedScene
            }}

            val collapseTransitionSet = TransitionSet()
            collapseTransitionSet.ordering = android.transition.TransitionSet.ORDERING_SEQUENTIAL


            val resetBounds = ChangeBounds()
            resetBounds.duration = 200
            collapseTransitionSet.addTransition(resetBounds)

            mTransitionManager!!.setTransition(mExpandedScene!!, mCollapsedScene!!, collapseTransitionSet)
            mTransitionManager!!.setTransition(mCollapsedScene!!, mExpandedScene!!, expandTransitionSet)
            mCollapsedScene?.enter()
        }
    }
}