def call(Map config = [:]) {
    def imageName = config.imageName ?: error("Image name is required")
    def imageTag = config.imageTag ?: 'latest'
    def credentials = config.credentials ?: 'docker-hub-credentials'
    
    echo "Pushing Docker image: ${imageName}:${imageTag}"
    
    withCredentials([usernamePassword(
        credentialsId: credentials,
        usernameVariable: 'DOCKER_USERNAME',
        passwordVariable: 'DOCKER_PASSWORD'
    )]) {
        sh """
            set -e
            echo "Logging in to Docker Hub..."
            echo "\$DOCKER_PASSWORD" | docker login -u "\$DOCKER_USERNAME" --password-stdin
            echo "Pushing ${imageName}:${imageTag}..."
            docker push ${imageName}:${imageTag}
            echo "Pushing ${imageName}:latest..."
            docker push ${imageName}:latest
            echo "Push completed successfully!"
        """
    }
}
