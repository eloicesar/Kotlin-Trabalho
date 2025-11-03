# 🚀 GUIA RÁPIDO - GameLib

## ✅ Checklist de Arquivos

Certifique-se de criar todos estes arquivos no projeto:

### 📁 Estrutura de Pastas

```
app/src/main/
├── AndroidManifest.xml
├── java/com/example/gamelib/
│   ├── MainActivity.kt
│   ├── data/
│   │   ├── local/
│   │   │   ├── AppDatabase.kt
│   │   │   ├── entity/
│   │   │   │   ├── Game.kt
│   │   │   │   └── Review.kt
│   │   │   └── dao/
│   │   │       ├── GameDao.kt
│   │   │       └── ReviewDao.kt
│   │   ├── remote/
│   │   │   ├── ApiModels.kt
│   │   │   └── GameApiService.kt
│   │   └── repository/
│   │       └── GameRepository.kt
│   └── ui/
│       ├── navigation/
│       │   └── AppNavigation.kt
│       ├── screens/
│       │   ├── HomeScreen.kt
│       │   ├── AddGameScreen.kt
│       │   ├── EditGameScreen.kt
│       │   ├── GameDetailScreen.kt
│       │   └── ApiSearchScreen.kt
│       ├── theme/
│       │   └── Theme.kt
│       └── viewmodel/
│           └── GameViewModel.kt
└── res/
    └── values/
        └── strings.xml
```

### 🔧 Passo 1: Criar Novo Projeto

1. Abra Android Studio
2. **New Project** → **Empty Activity**
3. Configure:
   - Name: `GameLib`
   - Package: `com.example.gamelib`
   - Language: `Kotlin`
   - Minimum SDK: `API 24`
   - Build configuration language: `Kotlin DSL (build.gradle.kts)`

### 📦 Passo 2: Configurar build.gradle.kts

**IMPORTANTE**: Substitua TODO o conteúdo do arquivo `app/build.gradle.kts` pelo código fornecido.

Após substituir, clique em **Sync Now** quando aparecer a mensagem no topo.

### 📝 Passo 3: Criar os Arquivos

Crie cada arquivo na ordem abaixo, copiando o código fornecido:

#### 1. Entidades (data/local/entity/)
- ✅ `Game.kt`
- ✅ `Review.kt`

#### 2. DAOs (data/local/dao/)
- ✅ `GameDao.kt`
- ✅ `ReviewDao.kt`

#### 3. Database (data/local/)
- ✅ `AppDatabase.kt`

#### 4. API (data/remote/)
- ✅ `ApiModels.kt`
- ✅ `GameApiService.kt`

#### 5. Repository (data/repository/)
- ✅ `GameRepository.kt`

#### 6. ViewModel (ui/viewmodel/)
- ✅ `GameViewModel.kt`

#### 7. Theme (ui/theme/)
- ✅ `Theme.kt`

#### 8. Screens (ui/screens/)
- ✅ `HomeScreen.kt`
- ✅ `AddGameScreen.kt`
- ✅ `EditGameScreen.kt`
- ✅ `GameDetailScreen.kt`
- ✅ `ApiSearchScreen.kt`

#### 9. Navigation (ui/navigation/)
- ✅ `AppNavigation.kt`

#### 10. MainActivity
- ✅ `MainActivity.kt` (substituir o existente)

#### 11. Manifest e Resources
- ✅ `AndroidManifest.xml` (substituir o existente)
- ✅ `strings.xml` (em res/values/)

### ▶️ Passo 4: Executar

1. Aguarde o Gradle Build terminar (barra inferior do Android Studio)
2. Selecione um emulador ou dispositivo físico
3. Clique em **Run** (▶️) ou pressione `Shift + F10`

## 🎯 Testando Funcionalidades

### ✅ CRUD de Jogos

1. **CREATE**:
   - Clique no botão (+) flutuante
   - Preencha: "The Witcher 3", "RPG", "PC", "2015"
   - Clique em "Salvar Jogo"

2. **READ**:
   - Veja o jogo aparecer na lista

3. **UPDATE**:
   - Clique no jogo
   - Clique no ícone de editar (✏️)
   - Altere para "PlayStation"
   - Salve

4. **DELETE**:
   - Nos detalhes do jogo
   - Clique no ícone de lixeira (🗑️)
   - Confirme

### ✅ CRUD de Avaliações

1. **CREATE**:
   - Entre nos detalhes de um jogo
   - Clique no botão (+) flutuante
   - Arraste o slider (nota)
   - Digite um comentário
   - Salve

2. **READ**:
   - Veja a lista de avaliações abaixo

3. **DELETE**:
   - Clique na lixeira da avaliação
   - Confirme

### ✅ API

1. Tela principal → Ícone de busca (🔍)
2. Digite "zelda" e clique em Buscar
3. Clique em um jogo da lista
4. Clique em "Importar"
5. Volte e veja o jogo importado

### ✅ Compartilhamento

1. Entre nos detalhes de um jogo
2. Clique no ícone de compartilhar (➦)
3. Escolha um app (WhatsApp, Email, etc.)
4. Veja os dados do jogo no app escolhido

## 🐛 Problemas Comuns

### Erro: "Unresolved reference"
- **Solução**: Certifique-se de que o pacote está correto (`com.example.gamelib`)
- Verifique se todos os imports estão corretos
- Build → Clean Project → Rebuild Project

### Erro: "Cannot resolve symbol Room"
- **Solução**: Sync do Gradle não completou
- File → Sync Project with Gradle Files
- Aguarde o download das dependências

### App crasha ao abrir
- **Solução**: Verifique o Logcat (View → Tool Windows → Logcat)
- Geralmente é problema de permissão INTERNET no Manifest
- Certifique-se de que `usesCleartextTraffic="true"` está no Manifest

### API não funciona
- **Solução**: 
  - Verifique a internet do emulador
  - Teste com WiFi ativo
  - API RAWG pode estar lenta, tente novamente

### Compartilhamento não abre nada
- **Solução**: 
  - Emulador pode não ter apps instalados
  - Teste em dispositivo físico
  - Ou instale apps no emulador (Gmail, etc.)

## 📱 Dicas para Apresentação

### Ordem Recomendada:

1. **Mostrar tela vazia** (2s)
   - "Biblioteca vazia inicialmente"

2. **Adicionar jogo manual** (30s)
   - Clicar no (+)
   - Preencher rapidamente
   - Mostrar na lista

3. **Buscar na API** (30s)
   - Buscar "mario"
   - Importar um jogo
   - Mostrar que apareceu na lista

4. **Ver detalhes** (20s)
   - Clicar em um jogo
   - Mostrar as informações

5. **Adicionar avaliação** (30s)
   - Clicar no (+)
   - Dar nota e comentário
   - Mostrar na lista

6. **Compartilhar** (20s)
   - Clicar em compartilhar
   - Mostrar opções

7. **Editar jogo** (20s)
   - Editar, marcar favorito
   - Mostrar estrela de favorito

8. **Filtrar favoritos** (10s)
   - Clicar no coração
   - Mostrar só favoritos

9. **Deletar** (15s)
   - Deletar jogo
   - Mostrar que sumiu

10. **Fechar e reabrir** (15s)
    - Fechar app completamente
    - Reabrir
    - "Dados persistidos com Room!"

## ⏱️ Tempo Total: ~3-4 minutos

---

## 🎓 Pontos Fortes para Destacar

1. **Arquitetura Limpa**
   - MVVM pattern
   - Repository pattern
   - Separação de camadas

2. **Tecnologias Modernas**
   - Jetpack Compose (UI declarativa)
   - Kotlin Coroutines (assíncrono)
   - Room (persistência)
   - Retrofit (networking)

3. **UX/UI**
   - Material Design 3
   - Navegação intuitiva
   - Feedback visual (loading, erros)
   - Confirmações (dialogs)

4. **Funcionalidades Extras**
   - Favoritos
   - Busca na API
   - Imagens dos jogos
   - Avaliações com notas
   - Relacionamento CASCADE

## 📊 Checklist de Critérios

- ✅ 2 Tabelas (Game + Review)
- ✅ CRUD Completo (Create, Read, Update, Delete)
- ✅ Room Database configurado
- ✅ Foreign Key + CASCADE
- ✅ API consumida (RAWG)
- ✅ Compartilhamento (Intent)
- ✅ Jetpack Compose
- ✅ Material Design 3
- ✅ Navegação
- ✅ ViewModel + StateFlow
- ✅ Persistência funciona

---

