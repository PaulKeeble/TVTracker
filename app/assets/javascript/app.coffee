mod = angular.module("trackerApp",['ngRoute','shows','configuration'])

mod.config(['$routeProvider',($routeProvider) ->
  $routeProvider.
    when('/shows', {
      templateUrl: '/shows/partial/list',
      controller: 'ShowListController'
    }).
    when('/shows/:showId', {
      templateUrl: '/shows/partial/detail',
      controller: 'ShowDetailController'
    }).
    when('/configuration', {
      templateUrl: '/config/partial/show',
      controller: 'ConfigController'
    }).
    otherwise({
      redirectTo: '/shows'
    })
]);
