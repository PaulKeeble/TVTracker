mod = angular.module("shows",[])

ShowListController = ($scope,$http,$location) ->
  $scope.shows = [
    {'name':'Mixed Watching','id':1,'episodes':{'watched':1,'total':45},'lastCreated':'2014-01-03'},
    {'name':'No episodes','id':3,'episodes':{'watched':0,'total':0},'lastCreated':null}
  ]
  
  $scope.userChanged = (newUser) ->
    console.log("received UCE " + newUser.name)

mod.controller("ShowListController",ShowListController)

