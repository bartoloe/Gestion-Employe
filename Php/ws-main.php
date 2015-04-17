<?php


require "Connexion.php";


if(isset($_GET['id']) && isset($_GET['token'])){

$_GET['id'] = htmlspecialchars($_GET['id']);
$_GET['token'] = htmlspecialchars($_GET['token']);

$obj = $connexion->prepare("SELECT token, datetoken FROM employe WHERE id_emp = :id");
$obj->execute(array(
'id' => $_GET['id']
));
$resultat = $obj->fetch(PDO::FETCH_ASSOC);

$now = time(); 

$datetoken = strtotime($resultat['datetoken']) ;   
$datediff = $now - $datetoken;
if($_GET['token'] == $resultat['token']){

if($datediff > 237200 ){//2 heures
echo 'token expired';
}
else
{
$obj = $connexion->prepare("SELECT id_ligue, nom_ligue FROM ligue WHERE id_empAdmin = :id");
$obj->execute(array(
'id' => $_GET['id']
));

$ligue = $obj->fetch(PDO::FETCH_ASSOC);

if($ligue){
$sth = $connexion->prepare("SELECT id_emp, nom_emp, prenom_emp, mail_emp FROM employe WHERE id_ligue = :ligue AND administrateur = '0' ORDER BY nom_emp");
$sth->execute(array(
'ligue' => $ligue['id_ligue']
));
}/*
// Si on veut un jour permettre l'authentification d'un employé dans l'application

else{      

$obj4 = $connexion->prepare("SELECT id_ligue FROM employe WHERE id_emp = :id");
$obj4->execute(array(
'id' => $_GET['id']
));

$ligue1 = $obj4->fetch(PDO::FETCH_ASSOC);

$obj2 = $connexion->prepare("SELECT id_ligue, nom_ligue FROM ligue WHERE id_ligue = :id");
$obj2->execute(array(
'id' => $ligue1['id_ligue']
));


$ligue = $obj2->fetch(PDO::FETCH_ASSOC);

$sth = $connexion->prepare("SELECT id_emp, nom_emp, prenom_emp, mail_emp FROM employe WHERE id_ligue = :ligue AND administrateur = '0' ORDER BY nom_emp");
$sth->execute(array(
'ligue' => $ligue['id_ligue']
));
}*/
header('Content-type: application/json');

$array = array();
while($employe = $sth->fetch(PDO::FETCH_ASSOC)){



$array['user'][]= $employe;
		}
		
$array['ligue'] = array('infoligue' => $ligue);
	echo json_encode($array);
}
}else{
echo 'Token invalid';
}


}else{
echo 'invalid parameters';
}
?>