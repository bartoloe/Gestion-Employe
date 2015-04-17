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

if(isset($_GET['id']) && isset($_GET['token']) && isset($_GET['nom'])&& isset($_GET['prenom'])&& isset($_GET['mail']) && isset($_GET['idupdate'])){
$_GET['nom'] = htmlspecialchars($_GET['nom']);
$_GET['prenom'] = htmlspecialchars($_GET['prenom']);
$_GET['mail'] = htmlspecialchars($_GET['mail']);
$_GET['id'] = htmlspecialchars($_GET['id']);
$_GET['token'] = htmlspecialchars($_GET['token']);
$_GET['idupdate'] = htmlspecialchars($_GET['idupdate']);



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

if($datediff > 23780000 ){//2 heures
echo 'Token expiré';
}
else
{
if(isset($_GET['pass'])){
$mdp = chaine_aleatoire(8);
$hash = sha1($mdp);
$req = $connexion->prepare('UPDATE employe SET nom_emp = :nom, prenom_emp = :prenom, mail_emp = :mail, hashPassword = :hash WHERE id_emp = :id');
        $req->execute(array(
          'nom' => $_GET['nom'],
		  'prenom' => $_GET['prenom'],
		  'mail' => $_GET['mail'],
		  'id' => $_GET['idupdate'],
		  'hash' => $hash
        ));
		echo $mdp;
}else{
$req = $connexion->prepare('UPDATE employe SET nom_emp = :nom, prenom_emp = :prenom, mail_emp = :mail WHERE id_emp = :id');
        $req->execute(array(
          'nom' => $_GET['nom'],
		  'prenom' => $_GET['prenom'],
		  'mail' => $_GET['mail'],
		  'id' => $_GET['idupdate']
        ));
		echo 'Updated';
	}	
}
}else{
echo 'Token not valid';
}

}else{

echo 'invalidmail';
}
}else{

echo 'Invalid parameters';
}


?>