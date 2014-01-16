window.Config = { }

mod = angular.module("config",[])

Config.ConfigController = ($scope,$http) -> 
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
    # filteredUsers = (u for u in $scope.users when u.name != user.name)
    # $scope.users = filteredUsers
  
  $scope.contains = (username) ->
    filteredUsers = (u for u in $scope.users when u.name == username)
    filteredUsers.length >0


mod.controller("ConfigController",Config.ConfigController)

