{{- define "mongo-helm.name" -}}
{{- default .Chart.Name .Values.nameOverride | trunc 63 | trimSuffix "-" -}}
{{- end -}}

{{- define "mongo-helm.fullname" -}}
{{ .Release.Name }}-{{ .Chart.Name }}
{{- end -}}

{{- define "mongo-helm.labels" -}}
app: {{ .Chart.Name }}
release: {{ .Release.Name }}
{{- end -}}