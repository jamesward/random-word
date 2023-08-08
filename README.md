# Random Word Service

## Dev Info

Run with restart:
```
./sbt ~reStart
```

Build the container:
```
pack build --builder=paketobuildpacks/builder:base random-word
```

Run the container:
```
docker run -p8080:8080 random-word
```
