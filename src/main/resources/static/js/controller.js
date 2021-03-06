var competencyCtrls = angular.module('competencyCtrls', []);
// 第一步 选择领域
competencyCtrls.controller('domainCtrl', function($scope, $http, $location, myFactory) {
	$scope.domains = [];
	$scope.competencys = [];
	var p = myFactory.getPosition();
	myFactory.setCompetencys(undefined);
	$scope.position = p;
	$scope.loadDomains = function() {
		$http.get('/getDomains').then(function(response) {
			$scope.domains = response.data;
			myFactory.setDomains($scope.domains);
		});
	};

	$scope.domains = myFactory.getDomains();
	if (!$scope.domains) {
		$scope.loadDomains();
	} else {
		angular.forEach($scope.domains,function(domain){
			if(domain.checked){
				$scope.competencys = domain.competencys;
				return;
			}
		});
	}

	$scope.checkDomain = function(domain) {
		domain.checked = !domain.checked;
		if(domain.checked){
			$scope.competencys = domain.competencys;
		} else {
			$scope.competencys = [];
		}
		
		angular.forEach(domain.competencys,function(competency){
			competency.checked = domain.checked;
		});
	};

	$scope.next = function() {
		if(!$scope.position || $scope.position == ''){
			toastr.warning('请输入职位！');
			return;
		}
		var checked = false;
		angular.forEach($scope.domains,function(domain){
			if(domain.checked){
				checked = true;
				return;
			}
		});
		
		if(!checked){
			toastr.warning('请选择职位关键贡献领域！');
			return;
		}
		myFactory.setPosition($scope.position);
		$location.url("/class");
	};
});
//第二部 根据类型选择胜任力
competencyCtrls.controller('classCtrl', function($scope, $http, $location, myFactory) {
	$scope.prev = function() {
		$location.url("/domain");
	};

	$scope.next = function() {
		var count = $scope.checkedCompetencys.length;
		if(count <= 0){
			toastr.warning('请选择胜任力！');
			return;
		}
		if(count > 9){
			toastr.warning('最多选出9项胜任力！');
			return;
		}
		myFactory.setCompetencys($scope.checkedCompetencys);
		$location.url("/warrper");
	};

	$scope.domains = myFactory.getDomains();
	$scope.position = myFactory.getPosition();
	if (!$scope.domains || !$scope.position) {
		$scope.prev();
	}
	
	$scope.checkedCompetencys = myFactory.getCompetencys();
	if (!$scope.checkedCompetencys && $scope.domains) {
		$scope.checkedCompetencys = [];
		var set = new Set();//辅助set
		angular.forEach($scope.domains,function(domain){
			if(domain.checked){
				angular.forEach(domain.competencys,function(competency){
					if(!set.has(competency.id)){
						set.add(competency.id);
						$scope.checkedCompetencys.push(competency);
					}
				});
			}
		});
		
		myFactory.setCompetencys($scope.checkedCompetencys);
	}

	$scope.classes = myFactory.getClasses();
	if(!$scope.classes){
		$scope.classes = {};
		$http.get('/getCompetenctClasses').then(function(response) {
			angular.forEach(response.data, function(competencyClass) {
				$scope.classes[competencyClass.id] = competencyClass;
			});
		});
	}

	$scope.check = function(competency) {
		var checkedCount = $scope.checkedCompetencys.length;
		if(checkedCount >= 9){
			toastr.warning('最多选出9项胜任力！');
			return;
		}
		for(var i=0;i<checkedCount;i++){
			if($scope.checkedCompetencys[i].id == competency.id){
				toastr.warning("该胜任力已选择！");
				return;
			}
		}
		$scope.checkedCompetencys.push(competency);
	};
	
	$scope.checkOut = function(competency) {
		for(var i=0;i<$scope.checkedCompetencys.length;i++){
			if($scope.checkedCompetencys[i].id == competency.id){
				$scope.checkedCompetencys.splice(i,1);
			}
		}
	};

});
//第三步 包装
competencyCtrls.controller('warrperCtrl', function($scope, $http, $location, myFactory) {
	$scope.prev = function() {
		myFactory.setCompetencys($scope.competencys);
		$location.url("/class");
	}
	
	$scope.next = function() {
		myFactory.setCompetencys($scope.competencys);
		$location.url("/evaluation");
	}
	$scope.position = myFactory.getPosition();
	
	$scope.competencys = myFactory.getCompetencys();
	if (!$scope.competencys) {
		$scope.prev();
	}
	
	$scope.delete = function(id){
		for(var i=0;i<$scope.competencys.length;i++){
			if($scope.competencys[i].id == id){
				$scope.competencys.splice(i,1);
				break;
			}
		}
	};
	
	$scope.export = function() {
		var exportData = [];
		angular.forEach($scope.competencys, function(competency) {
			exportData.push({
				'name': competency.name,
				'definition': competency.definition,
				'description': competency.description
			});
		});
		$http.post('/exportWarrper', {title:$scope.position,competencys:exportData}).then(function(data) {
			window.location.href = 'download?id='+data.data +'&title=胜任力模型--' + $scope.position;
		});
	};
	
	$scope.newPosition = function(){
		myFactory.reset();
		$location.url("/domain");
	};
});
//第四步 评价
competencyCtrls.controller('evaluationCtrl', function($scope, $http, $location, myFactory) {
	$scope.prev = function() {
		myFactory.setCompetencys($scope.competencys);
		$location.url("/warrper");
	}
	$scope.position = myFactory.getPosition();
	
	$scope.competencys = myFactory.getCompetencys();
	if (!$scope.competencys) {
		$scope.prev();
	}
	
	$scope.export = function() {
		var exportData = [];
		angular.forEach($scope.competencys, function(competency) {
			exportData.push({
				'name': competency.name,
				'evaluationCriterion': competency.evaluationCriterion,
				'question': competency.question
			});
		});
		$http.post('/exportEvaluation', {title:$scope.position,competencys:exportData}).then(function(data) {
			window.location.href = 'download?id='+data.data +'&title=胜任力模型--' + $scope.position;
		});
	};
	
	$scope.newPosition = function(){
		myFactory.reset();
		$location.url("/domain");
	};
});
