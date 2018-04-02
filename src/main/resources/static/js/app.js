var app = angular.module('competencyApp', ['ngRoute', 'xeditable', 'competencyCtrls']);
// 存放页面间跳转需要传递的数据
app.run(function(editableOptions) {
	editableOptions.theme = 'bs3'; // bootstrap3 theme. Can be also 'bs2', 'default'
});
app.factory('myFactory', function() {
	var domains;
	var competencys;
	var position;
	var classes;

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
	
	var setPosition = function(data) {
		position = data;
	};

	var getPosition = function() {
		return position;
	};
	
	var setClasses = function(data) {
		classes = data;
	};

	var getClasses = function() {
		return classes;
	};

	return {
		setDomains : setDomains,
		getDomains : getDomains,
		setCompetencys : setCompetencys,
		getCompetencys : getCompetencys,
		setClasses : setClasses,
		getClasses : getClasses,
		setPosition : setPosition,
		getPosition : getPosition
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
