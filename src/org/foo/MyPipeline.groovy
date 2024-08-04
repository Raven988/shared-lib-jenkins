package org.foo

class MyPipeline {

    MyPipeline(pipeline) {
        MyUtils.pipeline = pipeline
    }

    void execute(String name) {
        MyUtils.pipeline.stage('Building') {
            MyUtils.pipeline.echo(BuildStage.build())
        }

        MyUtils.pipeline.stage('Example') {
            MyUtils.pipeline.node {
                MyUtils.pipeline.script {
                    MyUtils.pipeline.echo(MyUtils.greet(name))
                    MyUtils.pipeline.echo(MyUtils.getGitRepoName())
                    MyUtils.sh('echo Hello from shell')
                    MyUtils.mkdir('exampleDir')
                }
            }
        }

        MyUtils.pipeline.stage('Parallel Testing') {
            MyUtils.pipeline.parallel(
                    'Unit Tests': {
                        MyUtils.pipeline.stage('Unit Tests') {
                            MyUtils.pipeline.echo(TestStage.unitTests())
                        }
                    },
                    'Integration Tests': {
                        MyUtils.pipeline.stage('Integration Tests') {
                            MyUtils.pipeline.echo(TestStage.integrationTests())
                        }
                    }
            )
        }

        MyUtils.pipeline.stage('Deploy Stage') {
            MyUtils.pipeline.echo(DeployStage.deploy())
        }
    }
}