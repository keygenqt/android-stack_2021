/*
 * Copyright 2021 Vitaliy Zarubin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
 
package com.keygenqt.stack_2021.network

import com.google.gson.*
import com.keygenqt.stack_2021.data.models.*
import com.skydoves.sandwich.*
import retrofit2.http.*

interface MainService {

    @GET("/users/{login}")
    suspend fun getUser(@Path("login") login: String): ApiResponse<JsonObject>

    @GET
    suspend fun fetchProjectList(@Url reposUrl: String): ApiResponse<List<Project>>
}
