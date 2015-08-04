<!DOCTYPE html
        PUBLIC "-//W3C//DTD XHTML 1.O Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>

<head>
    <title>BiZis-Meteo -- Error</title>
</head>

<body>
<div id="contenedorError">
    <h1>
        Site Error
    </h1>
    <p>
        A website error has occurred. Sorry for the temporary inconvenience.
    </p>
    <p>
        <em>
            <?php
                print_r($this->data['message']);
            ?>
        </em>
    </p>
</div>

</body>
</html>
