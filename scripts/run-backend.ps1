param(
    [string]$JavaHome,
    [int]$Port
)

# If a JavaHome wasn't provided, check environment variable
if (-not $JavaHome) { $JavaHome = $env:JAVA21_HOME }

# Try to discover common installation folders
if (-not $JavaHome) {
    $candidates = @(
        'C:\Program Files\Eclipse Adoptium',
        'C:\Program Files\Adoptium',
        'C:\Program Files\Java'
    )
    foreach ($base in $candidates) {
        if (Test-Path $base) {
            $found = Get-ChildItem -Path $base -Directory -ErrorAction SilentlyContinue | Where-Object { $_.Name -match 'jdk.*21' } | Select-Object -First 1
            if ($found) { $JavaHome = Join-Path $base $found.Name; break }
        }
    }
}

if (-not $JavaHome -or -not (Test-Path $JavaHome)) {
    Write-Host "Java 21 not found. Pass -JavaHome '<path-to-jdk21>' or set environment variable JAVA21_HOME." -ForegroundColor Red
    exit 1
}

Write-Host "Using JAVA_HOME = $JavaHome"

$env:JAVA_HOME = $JavaHome
$env:PATH = Join-Path $env:JAVA_HOME 'bin' + ';' + $env:PATH

if (-not (Test-Path .\mvnw.cmd)) {
    Write-Host "mvnw.cmd not found in project root. Run from repository root." -ForegroundColor Red
    exit 1
}

Write-Host "Starting Spring Boot via Maven wrapper... (logs will stream here)" -ForegroundColor Green

# If a port was provided, pass it to the Maven plugin as a JVM argument
if ($Port) {
    Write-Host "Starting on port $Port"
    $env:SERVER_PORT = "$Port"
    & .\mvnw.cmd spring-boot:run
} else {
    & .\mvnw.cmd spring-boot:run
}
