<?php


require "Connexion.php";


function chaine_aleatoire($nb_car, $chaine = 'azertyuiopqsdfghjklmwxcvbn123456789')
{
    $nb_lettres = strlen($chaine) - 1;
    $generation = '';
    for($i=0; $i < $nb_car; $i++)
    {
        $pos = mt_rand(0, $nb_lettres);
        $car = $chaine[$pos];
        $generation .= $car;
    }
    return $generation;
}

if(isset($_GET['id']) && isset($_GET['token']) && isset($_GET['nom'])&& isset($_GET['prenom'])&& isset($_GET['mail'])){
$_GET['nom'] = htmlspecialchars($_GET['nom']);
$_GET['prenom'] = htmlspecialchars($_GET['prenom']);
$_GET['mail'] = htmlspecialchars($_GET['mail']);
$_GET['id'] = htmlspecialchars($_GET['id']);
$_GET['token'] = htmlspecialchars($_GET['token']);


if(filter_var($_GET['mail'], FILTER_VALIDATE_EMAIL)){

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
else{

$obj = $connexion->prepare("SELECT id_ligue, nom_ligue FROM ligue WHERE id_empAdmin = :id");
$obj->execute(array(
'id' => $_GET['id']
));

$ligue = $obj->fetch(PDO::FETCH_ASSOC);


$mdp = chaine_aleatoire(8);
$hash = sha1($mdp);
$req = $connexion->prepare('INSERT INTO employe(nom_emp, prenom_emp, mail_emp, hashPassword, id_ligue) VALUES (:nom, :prenom, :mail, :password, :idligue)');
        $req->execute(array(
          'nom' => $_GET['nom'],
		  'prenom' => $_GET['prenom'],
		  'mail' => $_GET['mail'],
		  'password' => $hash,
		  'idligue' => $ligue['id_ligue']
        ));
		
echo $mdp;

}
}else{
echo 'invalid token';
}
}else{

echo 'invalidmail';
}
}else{
echo 'invalid parameters';
}


?>