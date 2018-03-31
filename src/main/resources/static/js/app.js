var app = angular.module('competencyApp', [ 'toaster', 'ngRoute', 'competencyCtrls' ]);

app.factory('myFactory', function() {
	var map = new Map();

	var _setter = function(data) {
		map = data;
	};

	var _getter = function() {
		return map;
	};

	return {
		set : _setter,
		get : _getter
	}
});

app.config(function ($routeProvider) {
    $routeProvider.when('/domain', {
        templateUrl: '/view/domain.html',
        controller: 'domainCtrl'
    }).when('/class', {
        templateUrl: '/view/class.html',
        controller: 'classCtrl'
    }).otherwise({
        redirectTo: '/domain'
    })
});
