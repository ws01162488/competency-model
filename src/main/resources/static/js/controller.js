var competencyCtrls = angular.module('competencyCtrls', []);
competencyCtrls.controller('domainCtrl', function ($scope, $http, $location,myFactory) {
	$scope.domains = [];
	$scope.competencys = [];
	
	$scope.loadDomains = function() {
		$http.get('/getDomains').then(function(response) {
			$scope.domains = response.data;
			myFactory.setDomains($scope.domains);
		});
	};
	
	$scope.domains = myFactory.getDomains();
	if(!$scope.domains){
		$scope.loadDomains();
	}

	$scope.checkDomain = function(domain) {
		domain.checked = !domain.checked;
		$scope.competencys = domain.competencys;
	};
	
	$scope.next = function(){
		$location.url("/class");
	};
});

competencyCtrls.controller('classCtrl', function ($scope,$http,$location,myFactory) {
	$scope.prev = function(){
		$location.url("/domain");
	};
	
	$scope.checkedCompetencys = myFactory.getCompetencys();
	if(!$scope.checkedCompetencys){
		$scope.checkedCompetencys = {};
	}
	$scope.next = function(){
		myFactory.setCompetencys($scope.checkedCompetencys);
		$location.url("/export");
	};
	
	$scope.domains = myFactory.getDomains();
	if(!$scope.domains){
		$scope.prev();
	}
	
	$scope.classes = {};
	$http.get('/getCompetenctClasses').then(function(response) {
		angular.forEach(response.data, function(competencyClass){
			$scope.classes[competencyClass.id] = competencyClass;
		});
		angular.forEach($scope.domains, function(domain){
			if(domain.checked){
				angular.forEach(domain.competencys, function(competency){
					competency.checked = false;
					$scope.classes[competency.competencyClass.id].competencys.push(competency);
				});
			}
		});
	});
	
	
	$scope.check = function(competency){
		competency.checked = !competency.checked;
		if(competency.checked){
			$scope.checkedCompetencys[competency.id] = competency;
		}else{
			delete $scope.checkedCompetencys[competency.id];
		}
		
	};
	
});

competencyCtrls.controller('exportCtrl', function ($scope, $http, $location,myFactory) {
	$scope.prev = function(){
		$location.url("/class");
	}
	
	$scope.competencys = myFactory.getCompetencys();
	if(!$scope.competencys){
		$scope.prev();
	}
});