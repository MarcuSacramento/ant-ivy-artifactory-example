O Desenvolvedor deverá configurar localmente o arquivo ivysetting.xml no diretório raiz do código fonte. a Configuração deverá ser semelhante a seguinte e não deverá ser commitado no git:


```
<?xml version="1.0" encoding="UTF-8"?>
<ivy-settings>
  <settings defaultResolver="main" />
  <!--Authentication required for publishing (deployment). 'Artifactory Realm' is the realm used by Artifactory so don't change it.-->
  <credentials host="artifactory.host" realm="Artifactory Realm" username="usuário-artifactory" passwd="senha -artifatoy-hash" />
  <resolvers>
    <chain name="main">
      <ibiblio name="public" m2compatible="true" root="http://artifactory.host/artifactory/libs-release"/>
      <ibiblio name="release" m2compatible="true" root="http://artifactory.host/artifactory/apps-release-local"/>
      <ibiblio name="snapshot" m2compatible="true" root="http://artifactory.host/artifactory/apps-snapshot-local"/>
    </chain>
  </resolvers>
</ivy-settings>
```

O Arquivo src/application-resources.properties definirá as versões da aplicação e do build bem como da publicação em release e snapshot

