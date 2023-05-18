package com.example.innobuzzapp

/**
 * Base Package
 *
 * Creating Base ViewModel and BaseFragment gives us a much more control to the app
 * suppose we are making an api call and we need to logout user if he is not authenticated (ie: 401)
 * so we can simply create a variable errorCode and live observe it in BaseFragment (as BaseViewmodel is injected in BaseFragment)
 * and if we get 401 we can logout user and destroy the activity very easily
 */


/**
 *The app flow is like that:
 *
 * first of all post list is checked in database if it is empty
 * then an api call is triggered and the data is save to the database
 * then an database call is triggered and data is loaded to screen (to make database as single source of truth)
 * whenever user click on refresh data :-
 * data is cleared from database
 * and again api all is made and data is saved to database
 * i would have used live observe room database but we are not frequently modifying (deleting,updating,inserting) the content of database so i used a single method
 * getPostListFromDatabase() to fetch data only one time
 */

/**
 * Utils Package
 *
 * It includes :-
 * extension function:- they are used to make life easier of developer and remove boiler plate code
 * Constants:- to declare each constant at single point to get more control over app.
 * State and sealed class to return and check that in which state is the call(api,database call)
 * NetworkUtil:- to check that whether the user has internet or not
 */