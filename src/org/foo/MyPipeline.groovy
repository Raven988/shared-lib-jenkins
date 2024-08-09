package org.foo

@Grab(group='com.moandjiezana.toml', module='toml4j', version='0.7.2')
import com.moandjiezana.toml.Toml

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

                    def tomlContent = '''
                        title = "TOML Example"

                        [owner]
                        name = "Tom Preston-Werner"
                        dob = 1979-05-27T07:32:00Z
                        '''
                        
                    def toml = new Toml().read(tomlContent)
                    def title = toml.getString("title")
                    def ownerName = toml.getString("owner.name")
                    
                    MyUtils.pipeline.echo("Title: ${title}")
                    MyUtils.pipeline.echo("Owner: ${ownerName}")
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