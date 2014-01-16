window.Todo = { }

class Todo.TodoCtrl
  @$inject: ['$scope']
  constructor: (@scope) ->
    @scope.todos = [
      {text:'learn angular', done:true},
      {text:'build an angular app', done:false}]
    @scope.addTodo = @addTodo
    @scope.remaining = @remaining
  
  addTodo: =>
    @scope.todos.push({text:@scope.todoText, done:false});
    @scope.todoText = '';
  
  remaining: =>
    count = 0
    angular.forEach(@scope.todos, (todo) -> count += todo.done ? 0 : 1)
    count

