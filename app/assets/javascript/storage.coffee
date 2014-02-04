mod = angular.module('storage',['ngCookies'])

class CookieStorageService
  constructor: (@$cookieStore) ->
  
  get: (key) ->
    @$cookieStore.get(key)
  
  put: (key,value) ->
    @$cookieStore.put(key,value)

class Html5StorageService
  constructor: () ->

  get: (key) ->
    angular.fromJson(window.localStorage.getItem(key))
  
  put: (key,value) ->
    window.localStorage.setItem(key,angular.toJson(value))

StorageServiceFactory = ($cookieStore) -> 
  if(typeof(Storage) isnt "undefined")
    new Html5StorageService()
  else
    new CookieStorageService($cookieStore)

mod.factory('storageService',['$cookieStore',StorageServiceFactory])