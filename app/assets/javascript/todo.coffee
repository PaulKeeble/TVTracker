window.Todo = { }

Todo.TodoCtrl = ($scope) ->
  $scope.todos = [{text:'learn angular', done:true},{text:'build an angular app', done:false}]
  
  $scope.addTodo = ->
    $scope.todos.push({text:$scope.todoText, done:false});
    $scope.todoText = '';
  
  $scope.remaining = ->
    count = 0
    angular.forEach($scope.todos, (todo) -> count += todo.done ? 0 : 1)
    count