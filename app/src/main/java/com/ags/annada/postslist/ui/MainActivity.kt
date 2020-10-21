package com.ags.annada.postslist.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ags.annada.postslist.R
import com.ags.annada.postslist.ui.comments.CommentsFragment
import com.ags.annada.postslist.ui.postsusers.PostsWithUserFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), PostsWithUserFragment.OnPostSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PostsWithUserFragment.newInstance())
                .commitNow()
        }
    }

    override fun onAttachFragment(fragment: Fragment) {
        if (fragment is PostsWithUserFragment) {
            fragment.setOnPostSelectedListener(this)
        }
    }

    override fun onPostSelected(postId: Int) {
        val newFragment = CommentsFragment()
        val args = Bundle()
        args.putInt(CommentsFragment.ARG_POST_ID, postId)
        newFragment.arguments = args

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, newFragment)
        transaction.addToBackStack(null)

        transaction.commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
