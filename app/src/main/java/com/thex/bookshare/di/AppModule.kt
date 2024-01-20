package com.thex.bookshare.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.postgrest
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideSupabaseClient(): SupabaseClient {
        //TODO::: use secure way / buildConfig to store secure keys
        val key =
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6IndraHJ0ZWZ0cWF6ZnlpdWt0ZWFiIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MDUwNDkwMDQsImV4cCI6MjAyMDYyNTAwNH0.y54ZPuON5rehEqAEGtcAf_6DvJLubv7lnoTTc4VR8ao"
        return createSupabaseClient(
            supabaseUrl = "https://wkhrteftqazfyiukteab.supabase.co",
            supabaseKey = key
        )
        {
            this.install(Postgrest)
            this.install(Auth)
        }
    }

    @Provides
    @Singleton
    fun provideSupabaseDatabase(client: SupabaseClient): Postgrest {
        return client.postgrest
    }

    @Provides
    @Singleton
    fun provideSupabaseAuth(client: SupabaseClient): Auth {
        return client.auth
    }


}