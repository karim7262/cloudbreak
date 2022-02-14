{%- from 'telemetry/settings.sls' import telemetry with context %}
{% set clouderaArchiveUrl = 'https://archive.cloudera.com' %}
/etc/yum.repos.d/cdp-infra-tools.repo:
  file.managed:
    - source: salt://telemetry/template/cdp-infra-tools.repo.j2
    - template: jinja
    - mode: 640
    - context:
         repoName: "{{ telemetry.repoName }}"
         repoBaseUrl: "{{ telemetry.repoBaseUrl }}"
         repoGpgKey: "{{ telemetry.repoGpgKey }}"
         repoGpgCheck: {{ telemetry.repoGpgCheck }}

/opt/salt/scripts/cdp-telemetry-deployer.sh:
    file.managed:
        - source: salt://telemetry/scripts/cdp-telemetry-deployer.sh
        - template: jinja
        - mode: '0750'{% if telemetry.desiredCdpTelemetryVersion %}
upgrade_cdp_telemetry_component:
    cmd.run:
        - name: '/opt/salt/scripts/cdp-telemetry-deployer.sh upgrade -c cdp-telemetry -v {{ telemetry.desiredCdpTelemetryVersion }};exit 0'
        - onlyif: "curl --max-time 30 -s -k {{ clouderaArchiveUrl }} > /dev/null" {% if telemetry.proxyUrl %}
        - env:
          - https_proxy: {{ telemetry.proxyUrl }}{% if telemetry.noProxyHosts %}
          - no_proxy: {{ telemetry.noProxyHosts }}{% endif %}{% endif %}
{% endif %}{% if telemetry.desiredCdpLoggingAgentVersion %}
upgrade_cdp_logging_agent_component:
    cmd.run:
        - name: '/opt/salt/scripts/cdp-telemetry-deployer.sh upgrade -c cdp-logging-agent -v {{ telemetry.desiredCdpLoggingAgentVersion }};exit 0'
        - onlyif: "curl --max-time 30 -s -k {{ clouderaArchiveUrl }} > /dev/null" {% if telemetry.proxyUrl %}
        - env:
          - https_proxy: {{ telemetry.proxyUrl }}{% if telemetry.noProxyHosts %}
          - no_proxy: {{ telemetry.noProxyHosts }}{% endif %}{% endif %}{% endif %}