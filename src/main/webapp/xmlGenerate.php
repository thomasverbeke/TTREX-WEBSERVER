<?php
header('Access-Control-Allow-Origin: *');
header('Access-Control-Allow-Headers: Content-Type');
// @Author: Thomas Verbeke (thomasverbeke@gmail.com)


$URL = "http://eng.studev.groept.be/thesis/a12_coptermotion/upload.php";
$PATH_TO_XML_FILE = "tmp/parsedKML.xml"; //path to tmp XML file

//RETRIEVE JSON ENCODED ARRAY FROM POST
print "parseKML.php  \n";
$data = file_get_contents( "php://input" ); 
$data = json_decode( $data ); //DATA IS ARRAY NOW

//var_dump($data);

// Creates the Document.
$dom = new DOMDocument('1.0', 'UTF-8');

// Creates a KML Document element and append it to the KML element.
$dnode = $dom->createElement('Document');
$docNode = $dom->appendChild($dnode);

//Create groups
$groups = $dom->createElement('groups');
$groupsNode = $docNode->appendChild($groups);
  
// Iterate trough array & add groups
for ($i=0; $i<count($data); $i++){
  $group = $dom->createElement('group');
  $groupNode = $groupsNode->appendChild($group);
	
  // Creates an id attribute and assign it the value of id column.
  $groupNode->setAttribute('id',  $data[$i]->{"id"}); //replace $data with real data
  
  $groupName = $dom->createElement('groupName', $data[$i]->{"groupName"});	
  $groupNode->appendChild($groupName);

  // Creates a Latitude element.
  $latNode = $dom->createElement('startLatitude', $data[$i]->{"startLatitude"});
  $groupNode->appendChild($latNode);
  
  // Creates a Longitude element.
  $lngNode = $dom->createElement('startLongitude', $data[$i]->{"startLongitude"});
  $groupNode->appendChild($lngNode);
  
}

//end loop


  
//parse a KML&XMLfile locally on the server inside tmp folder
$kmlOutput = $dom->save("upload/configure.xml");

echo "File Generated on http://eng.studev.groept.be/thesis/a12_coptermotion/upload/configure.xml";
//$data = array('ID' => 'someID', 'xml' => "@". "tmp/configure.xml;type=text/xml");
//
//echo "Database Queries complete; starting curl...";
//// Setup cURL
//$ch = curl_init($URL);
//curl_setopt($ch,CURLOPT_POST,true);
//curl_setopt($ch,CURLOPT_POSTFIELDS,$data);
//curl_setopt($ch,CURLOPT_RETURNTRANSFER,true);
//curl_setopt($ch,CURLOPT_TIMEOUT,8);
//curl_setopt($ch,CURLOPT_USERPWD, 'admin:1234'); 
////curl_setopt($ch, CURLOPT_VERBOSE, true);
////curl_setopt($ch, CURLOPT_STDERR, fopen('php://output', 'w+'));
//
//// Execute cURL (Send XML file to receiving 'upload.php' on the server side
//$output = curl_exec($ch);
//
///**
// * Check for errors
// */
//if ( curl_errno($ch) ) {
//	$result = 'ERROR -> ' . curl_errno($ch) . ': ' . curl_error($ch);
//} else {
//	$returnCode = (int)curl_getinfo($ch, CURLINFO_HTTP_CODE);
//	switch($returnCode){
//		case 404:
//			$result = 'ERROR -> 404 Not Found';
//			break;
//		default:
//			break;
//	}
//}
//
///**
// * Close the handle
// */
//curl_close($ch);
	
//print $output;
?>