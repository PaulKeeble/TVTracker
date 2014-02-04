mod = angular.module("users",['storage'])

UserController = ($scope,userService) ->
  $scope.users = userService.users
  $scope.currentUser=userService.currentUser

  $scope.onChange = () ->
    userService.updateCurrentUser($scope.currentUser)
    $scope.onUserChange()($scope.currentUser)


UserSelectorDirective = () ->
  {
    restrict: 'EA',
    scope: {
      onUserChange: '&'
    },
    controller: ['$scope','userService', UserController ],
    templateUrl:'/config/partial/userSelector'
  }


class UserService
  constructor: (@storageService)->
    @users= [{'name':'Laura'},{'name':'Paul'}]
    @currentUser= this.defaultCurrentUser()

  updateCurrentUser: (newUser) ->
    foundUser = this.findUser(newUser)
    if(foundUser?)
      @currentUser=foundUser
      @storageService.put("currentUser",foundUser)
  
  findUser: (toFind) ->
    matchingUsers = (u for u in @users when u.name == toFind.name)
    if(matchingUsers? and matchingUsers.length>0)
      matchingUsers[0]
    else
      null
    
  defaultCurrentUser: () ->
    loaded = @storageService.get("currentUser")
    console.log(loaded)
    if(loaded?)
      this.findUser(loaded)
    else
      @users[0]
  
UserServiceFactory = (storageService) -> new UserService(storageService)

mod.factory('userService', ['storageService',UserServiceFactory])
mod.controller('userController',UserController)
mod.directive('userSelector',UserSelectorDirective)
