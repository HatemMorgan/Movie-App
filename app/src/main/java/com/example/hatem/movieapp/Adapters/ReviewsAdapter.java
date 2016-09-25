package com.example.hatem.movieapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hatem.movieapp.ORM.Reviews;
import com.example.hatem.movieapp.R;
import com.example.hatem.movieapp.Utilities.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.List;




public class ReviewsAdapter extends BaseAdapter {

    private List<Reviews> reviewList ;
    private Context context ;
    private LayoutInflater inflater ;
    private  ImageView authorImageView;
    private  TextView authorName;
    private  TextView reviewContent;

    public  ReviewsAdapter (Context context , List<Reviews> reviewList){
        this.context = context ;
        this.reviewList = reviewList ;
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return reviewList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
       View rootView = inflater.inflate(R.layout.review_list_item,null);

        Reviews review = reviewList.get(i);

        authorImageView = (ImageView) rootView.findViewById(R.id.imageView_Review_AuthorImage);
        authorName = (TextView) rootView.findViewById(R.id.textView_Review_authorName);
        reviewContent = (TextView) rootView.findViewById(R.id.textView_Review_content);

        String imagePath = review.getAuthor_profile_path() ;

        if(imagePath.equals("Null")){
//            authorImageView.setImageResource(R.mipmap.emptyprofilepicture);
            Picasso.with(context).load(R.mipmap.emptyprofilepicture).transform(new CircleTransform()).into(authorImageView);
        }else{
//            Picasso.with(context).load("http://image.tmdb.org/t/p/w185"+review.getAuthor_profile_path()).into(authorImageView);
            Picasso.with(context).load("http://image.tmdb.org/t/p/w185"+review.getAuthor_profile_path()).transform(new CircleTransform()).into(authorImageView);
        }


        authorName.setText(review.getAuthorName());
        reviewContent.setText(review.getReviewContent());



        return rootView;

    }
}
