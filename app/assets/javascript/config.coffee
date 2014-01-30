mod = angular.module("configuration",[])

ConfigController = ($scope,$http) -> 
  $scope.users = []
  
  updateUsers = () -> $http.get('/configuration/users').success((data) ->
    $scope.users = data.users
  )
  
  updateUsers()
  
  $scope.add = () ->
    if($scope.inputUsername? and $scope.inputUsername.replace(/\ /g, "")  != '' and (not $scope.contains($scope.inputUsername)))
      newUser = {'name':$scope.inputUsername}
      $http.post('/configuration/users',newUser).success((data) ->
        updateUsers()
      )
      $scope.inputUsername = ''
    
  $scope.delete = (user) ->
    $http.delete('/configuration/users/'+user.name).success((data) ->
      updateUsers()
    )
  
  $scope.contains = (username) ->
    filteredUsers = (u for u in $scope.users when u.name == username)
    filteredUsers.length >0
 
  
  $scope.seriesDirectory = {'path':'','valid':false}
  
  $http.get('/configuration/directories').success((data) ->
    $scope.seriesDirectory = data
  )
 
  $scope.validText = () ->
    if($scope.seriesDirectory.valid)
      "Valid"
    else
      "Not valid"
  
  $scope.changeSeriesDirectory = () ->
    $http.post('/configuration/directories',{'path':$scope.seriesDirectory.path}).success((data) ->
      $scope.seriesDirectory = data
    )
  
  $scope.valid = () ->
    $scope.users.length >=1 and $scope.seriesDirectory.valid

mod.controller("ConfigController",ConfigController)

