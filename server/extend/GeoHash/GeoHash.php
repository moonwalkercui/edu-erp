<?php
namespace GeoHash;
// 类
class GeoHash {

    private $hash;

    private $latitude;
    private $longitude;
    private $precision;

    /**
     * Returns the hash
     * @return string
     */
    public function getHash() {
        if(!$this->hash) {
            if(empty($this->latitude)) throw new \Exception("Latitude is required");
            if(empty($this->longitude)) throw new \Exception("Longitude is required");
            $this->hash = $this->createHash();
        }
        return $this->hash;
    }

    /**
     * Set a hash, this will clear any latitude/longitude or precision set
     * @return GeoHash
     */
    public function setHash($hash) {
        $this->hash = $hash;
        $this->parseHash();
        return $this;
    }

    /**
     * Get the latitude
     * @return float
     */
    public function getLatitude() {
        return $this->latitude;
    }

    /**
     * Set a latitude, this will clear any hash
     * @return GeoHash
     */
    public function setLatitude($latitude) {
        $this->hash = null;
        $this->latitude = $latitude;
        return $this;
    }

    /**
     * Get the longitude
     * @return float
     */
    public function getLongitude() {
        return $this->longitude;
    }

    /**
     * Set a latitude, this will clear any hash
     * @return GeoHash
     */
    public function setLongitude($longitude) {
        $this->hash = null;
        $this->longitude = $longitude;
        return $this;
    }

    /**
     * Gets the precision
     * @return float
     */
    public function getPrecision() {
        return $this->precision;
    }

    /**
     * Set a precision, clears any hash
     * @return GeoHash
     */
    public function setPrecision($precision) {
        $this->hash = null;
        $this->precision = $precision;
        return $this;
    }

    /**
     * Return the hash, obviously, to print out
     * @return string
     */
    public function __toString() {
        return $this->getHash();
    }

    private function clearCoords() {
        $this->latitude  = null;
        $this->longitude = null;
        $this->precision = null;
    }

    private function createHash() {
        $table = "0123456789bcdefghjkmnpqrstuvwxyz";
        $lng = $this->longitude;
        $lat = $this->latitude;
        if(isset($this->precision)) {
            $p = $this->precision;
        } else {
            $lap = strlen($lat)-strpos($lat,".");
            $lop = strlen($lng)-strpos($lng,".");
            $p = $this->precision = pow(10,-max($lap-1,$lop-1,0))/2;
        }
        $minlat =  -90;
        $maxlat =   90;
        $minlng = -180;
        $maxlng =  180;
        $latE   =   90;
        $lngE   =  180;
        $i=0;
        $hash = "";
        $error = 180;
        while($error>=$p) {
            $chr = 0;
            for($b=4;$b>=0;--$b) {
                if((1&$b) == (1&$i)) { // even char, even bit OR odd char, odd bit...a lng
                    $next = ($minlng+$maxlng)/2;
                    if($lng>$next) {
                        $chr |= pow(2,$b);
                        $minlng = $next;
                    } else {
                        $maxlng = $next;
                    }
                    $lngE /= 2;
                } else { // odd char, even bit OR even char, odd bit...a lat
                    $next = ($minlat+$maxlat)/2;
                    if($lat>$next) {
                        $chr |= pow(2,$b);
                        $minlat = $next;
                    } else {
                        $maxlat = $next;
                    }
                    $latE /= 2;
                }
            }
            $hash .= $table[$chr];
            $i++;
            $error = min($latE,$lngE);
        }
        return $hash;
    }

    private function parseHash() {
        $table = "0123456789bcdefghjkmnpqrstuvwxyz";
        $hash = $this->hash;
        $this->clearCoords();
        $minlat =  -90;
        $maxlat =   90;
        $minlng = -180;
        $maxlng =  180;
        $latE   =   90;
        $lngE   =  180;
        for($i=0,$c=strlen($hash);$i<$c;$i++) {
            $v = strpos($table,$hash[$i]);
            if(1&$i) {
                if(16&$v)$minlat = ($minlat+$maxlat)/2; else $maxlat = ($minlat+$maxlat)/2;
                if(8&$v) $minlng = ($minlng+$maxlng)/2; else $maxlng = ($minlng+$maxlng)/2;
                if(4&$v) $minlat = ($minlat+$maxlat)/2; else $maxlat = ($minlat+$maxlat)/2;
                if(2&$v) $minlng = ($minlng+$maxlng)/2; else $maxlng = ($minlng+$maxlng)/2;
                if(1&$v) $minlat = ($minlat+$maxlat)/2; else $maxlat = ($minlat+$maxlat)/2;
                $latE /= 8;
                $lngE /= 4;
            } else {
                if(16&$v)$minlng = ($minlng+$maxlng)/2; else $maxlng = ($minlng+$maxlng)/2;
                if(8&$v) $minlat = ($minlat+$maxlat)/2; else $maxlat = ($minlat+$maxlat)/2;
                if(4&$v) $minlng = ($minlng+$maxlng)/2; else $maxlng = ($minlng+$maxlng)/2;
                if(2&$v) $minlat = ($minlat+$maxlat)/2; else $maxlat = ($minlat+$maxlat)/2;
                if(1&$v) $minlng = ($minlng+$maxlng)/2; else $maxlng = ($minlng+$maxlng)/2;
                $latE /= 4;
                $lngE /= 8;
            }
        }
        $this->latitude  = round(($minlat+$maxlat)/2, max(1, -round(log10($latE)))-1);
        $this->longitude = round(($minlng+$maxlng)/2, max(1, -round(log10($lngE)))-1);
        $this->precision = max($latE,$lngE);
    }
}