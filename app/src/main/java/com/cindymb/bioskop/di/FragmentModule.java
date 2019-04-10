package com.cindymb.bioskop.di;


import com.cindymb.bioskop.DetailsFragment;
import com.cindymb.bioskop.MainFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract MainFragment mainFragment();

    @ContributesAndroidInjector
    abstract DetailsFragment detailsFragment();
}
