package hajjcare.hajj.com.hajjcare.views.fragments;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.makeramen.roundedimageview.RoundedImageView;


import hajjcare.hajj.com.hajjcare.R;
import hajjcare.hajj.com.hajjcare.control.Controller;
import hajjcare.hajj.com.hajjcare.control.UIController;
import hajjcare.hajj.com.hajjcare.models.network.HelpMeRequest;
import hajjcare.hajj.com.hajjcare.networklayer.OnCallComplete;
import hajjcare.hajj.com.hajjcare.views.activity.HomeActivity;


/**
 * Created by amra on 2/5/2018.
 */

public class HomeFragment extends BaseFragment {

    private View containerView;
    private Button loginButton;
    private RoundedImageView postImageView;
    private ImageView nextArrow;
    private ImageView backArrow;
    private ImageView insurance;
//    private ArrayList<Data> data;
    private ImageView helpMe;
    private ImageView offers;
    private ImageView ourLocations;
    private ProgressBar progressBar;
    private int photoNumber = 0;
    private ImageView call;
    private ImageView book;
    private int currentPage = 1;
    private int lastPage = 2;
    private Resources res;
    private DisplayMetrics dm;
    private Configuration conf;
    private String lang;
    private Location location;
    private boolean helpRequestSent = false;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        containerView = inflater.inflate(R.layout.fragment_home, container, false);

        postImageView = (RoundedImageView) containerView.findViewById(R.id.user_photo);
        postImageView.setBorderColor(ContextCompat.getColor(getActivity(), R.color.white));
        nextArrow = (ImageView) containerView.findViewById(R.id.next_arrow);
        backArrow = (ImageView) containerView.findViewById(R.id.back_arrow);
        insurance = (ImageView) containerView.findViewById(R.id.insurance);
        offers = (ImageView) containerView.findViewById(R.id.offers);
        helpMe = (ImageView) containerView.findViewById(R.id.booking);


        progressBar = (ProgressBar) containerView.findViewById(R.id.progress_bar);
        ourLocations = (ImageView) containerView.findViewById(R.id.our_locations);
        offers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(getActivity(), OffersActivity.class));
            }
        });
        helpMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                location =(HomeActivity)getActivity().getLocation();

                if(location!= null)
                sendLocation();
//                startActivity(new Intent(getActivity(), BookingActivity.class));
            }
        });

        insurance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent insuranceIntent = new Intent(getActivity(), InsuranceActivity.class);
//                startActivity(insuranceIntent);
            }
        });

        nextArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                if (data != null) {
//
//                    if (photoNumber == (data.size() - 1) && currentPage < lastPage) {
//                        currentPage++;
//                        Controller.getInstance().getPosts(new OnCallComplete() {
//                            @Override
//                            public void onSuccess(Object object) {
//
//                                PostsResponse postsResponse = (PostsResponse) object;
//                                lastPage = postsResponse.getPagination().getLast_page();
//                                data.addAll(postsResponse.getData());
//                                showNextData();
//
//                            }
//
//                            @Override
//                            public void onFailure(Exception appException) {
//
//                                UIController.handleActivtyResponseError(appException, getActivity(), false, true);
//                            }
//                        }, lang, currentPage);


//                    } else {
//                        showNextData();
//                    }


//                } else {
//                    Toast.makeText(getActivity(), getString(R.string.str_please_waite), Toast.LENGTH_SHORT).show();
//                }

            }
        });


        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                if (data != null) {
//                    photoNumber--;
//                    if (photoNumber >= 0) {
//                        progressBar.setVisibility(View.VISIBLE);
//                        postImageView.setVisibility(View.GONE);
//                        Picasso.with(getActivity()).load(data.get(photoNumber).getAttachment()).into(postImageView, new com.squareup.picasso.Callback() {
//                            @Override
//                            public void onSuccess() {
//                                progressBar.setVisibility(View.GONE);
//                                postImageView.setVisibility(View.VISIBLE);
//                            }
//
//                            @Override
//                            public void onError() {
//                                progressBar.setVisibility(View.GONE);
//                                postImageView.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.mipmap.error));
//                            }
//                        });
//                    } else {
//                        photoNumber++;
//                        Toast.makeText(getActivity(), getString(R.string.str_last_image), Toast.LENGTH_SHORT).show();
//                    }
//
//                } else {
//                    Toast.makeText(getActivity(), getString(R.string.str_please_waite), Toast.LENGTH_SHORT).show();
//                }

            }
        });

        ourLocations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(getActivity(), OurLocationsActivity.class));
            }
        });

        return containerView;
    }

    public void sendLocation() {
        Controller.getInstance().helpMe(new OnCallComplete() {
            @Override
            public void onSuccess(Object object) {

                helpRequestSent = true;
                    Toast.makeText(getActivity(), "RequestSent", Toast.LENGTH_SHORT).show();


//                    getActivity().finish();
            }

            @Override
            public void onFailure(Exception appException) {

                UIController.handleActivtyResponseError(appException, getActivity(), false, true);
            }
        }, new HelpMeRequest(Controller.getMyId(), String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude())));
    }

    private void showNextData() {
        photoNumber++;
//        if (photoNumber < data.size()) {
//            progressBar.setVisibility(View.VISIBLE);
//            progressBar.setVisibility(View.GONE);
//            Picasso.with(getActivity()).load(data.get(photoNumber).getAttachment()).into(postImageView, new com.squareup.picasso.Callback() {
//                @Override
//                public void onSuccess() {
//                    progressBar.setVisibility(View.GONE);
//                    postImageView.setVisibility(View.VISIBLE);
//                }
//
//                @Override
//                public void onError() {
//                    progressBar.setVisibility(View.GONE);
//                    postImageView.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.mipmap.error));
//                }
//            });
//        } else {
//            photoNumber--;
//            Toast.makeText(getActivity(), getString(R.string.str_last_image), Toast.LENGTH_SHORT).show();
//
//        }
    }

    @Override
    public void onResume() {
        super.onResume();

//        if (StarSmilesApplicationClass.getUserProfile() == null) {
//            Controller.getInstance().getProfileData(new OnCallComplete() {
//                @Override
//                public void onSuccess(Object object) {
//
//                    ProfileResponse profileResponse = (ProfileResponse) object;
//                    StarSmilesApplicationClass.setUserProfile(profileResponse);
//
////                    Picasso.with(getActivity()).load(StarSmilesApplicationClass.getInstance().getBaseURL() + profileResponse.getUser().getAvatar())
////                            .placeholder(R.drawable.progress_animation)
////                            .error(R.mipmap.name_icon).into(((BaseActivity) getActivity()).getUserAvatar());
//
//                    ((BaseActivity) getActivity()).getUserName().setText(profileResponse.getUser().getName());
//                    ((BaseActivity) getActivity()).getTitleTextView().setText(profileResponse.getUser().getName());
//                }
//
//                @Override
//                public void onFailure(Exception appException) {
//
//                    UIController.handleActivtyResponseError(appException, getActivity(), false, true);
//                }
//            });

//        }
//        res = getResources();
//        dm = res.getDisplayMetrics();
//        conf = res.getConfiguration();
//
//        if (conf.locale.getDisplayLanguage().toLowerCase().equals(new Locale(Constants.LANGUAGE_AR).getDisplayLanguage().toLowerCase())) {
//            lang = Constants.LANGUAGE_AR;
//        } else {
//            lang = Constants.LANGUAGE_EN;
//        }
//
//        if (data == null && currentPage <= lastPage) {
//
//            Controller.getInstance().getPosts(new OnCallComplete() {
//                @Override
//                public void onSuccess(Object object) {
//
//                    PostsResponse postsResponse = (PostsResponse) object;
//                    data = postsResponse.getData();
//                    lastPage = postsResponse.getPagination().getLast_page();
//                    setSlideImage();
//
//                }
//
//                @Override
//                public void onFailure(Exception appException) {
//
//                    UIController.handleActivtyResponseError(appException, getActivity(), false, true);
//                }
//            }, lang, currentPage);
//
//
//        } else {
//
//            setSlideImage();
//        }
    }

    private void setSlideImage() {
//        Picasso.with(getActivity()).load(data.get(0).getAttachment()).into(postImageView, new com.squareup.picasso.Callback() {
//            @Override
//            public void onSuccess() {
//                progressBar.setVisibility(View.GONE);
//                postImageView.setVisibility(View.VISIBLE);
//            }
//
//            @Override
//            public void onError() {
//                progressBar.setVisibility(View.GONE);
//                postImageView.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.mipmap.error));
//            }
//        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case Controller.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    location = Controller.getLocation(getActivity());
                }
            }
        }
        sendLocation();
    }

    public boolean isHelpRequestSent() {
        return helpRequestSent;
    }

    public void setHelpRequestSent(boolean helpRequestSent) {
        this.helpRequestSent = helpRequestSent;
    }
}
