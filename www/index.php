<?php

class APILogWriter {
    public function write($message, $level = \Slim\Log::DEBUG) {
        # Simple for now
        echo $level.': '.$message.'<br />';
    }
}

require 'Slim/Slim/Slim.php';
\Slim\Slim::registerAutoloader();

$app = new Slim\Slim(array(
        'mode' => 'development',
        'log.enabled' => true,
        'log.level' => \Slim\Log::DEBUG,
        'log.writer' => new APILogWriter()
    )
);

require_once __DIR__ . '/app.php';

$app->run();

/* End of file index.php */
