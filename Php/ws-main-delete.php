<?php


require "Connexion.php";


if(isset($_GET['id']) && isset($_GET['token']) && isset($_GET['idelete'])){
$_GET['id'] = htmlspecialchars($_GET['id']);
$_GET['token'] = htmlspecialchars($_GET['token']);
$_GET['idelete'] = htmlspecialchars($_GET['idelete']);

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
echo 'Token expiré';
}
else
{

$sth = $connexion->prepare("DELETE FROM employe WHERE id_emp = :id_emp");
                  $sth->execute(array(
                  'id_emp' => $_GET['idelete']
                  ));
		
}
}else{

echo 'Token invalid';
}
}else{
echo 'Invalid parameters';
}


?>