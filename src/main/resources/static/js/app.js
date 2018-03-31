var app = angular.module('competencyApp', [ 'toaster', 'ngRoute', 'competencyCtrls' ]);

app.factory('myFactory', function() {
	var domains;
	var competencys;

	var setDomains = function(data) {
		domains = data;
	};

	var getDomains = function() {
		return domains;
	};
	
	var setCompetencys = function(data) {
		competencys = data;
	};

	var getCompetencys = function() {
		return competencys;
	};

	return {
		setDomains : setDomains,
		getDomains : getDomains,
		setCompetencys : setCompetencys,
		getCompetencys : getCompetencys
	}
});

app.config(function ($routeProvider) {
    $routeProvider.when('/domain', {
        templateUrl: '/view/domain',
        controller: 'domainCtrl'
    }).when('/class', {
        templateUrl: '/view/class',
        controller: 'classCtrl'
    }).when('/export', {
        templateUrl: '/view/export',
        controller: 'exportCtrl'
    }).otherwise({
        redirectTo: '/domain'
    })
});
