# Contributing Instructions

## Setting up the development environment

The easiest way to get the development running is to use the `Open in Gitpod` button that
can be found on the project's [README.md](README.md). The project also has valid docker and
Vagrant configurations and can be run using either of them.

If you are using Gitpod or Vagrant, then you need to run `./tools/run-dev` in the root to
fire up the development servers. For docker users, the command is `docker-compose up`.

## Deployment instructions

The project has a separate frontend and backend and are deployed separately. Both the
backend and frontend are deployed as two separate apps on Heroku. Since this is a monorepo,
they need to be pushed to their remotes using the subtree strategy since Heroku does not
support deployment from monorepos.

**NOTE**: You will need to change the remote URLs since only our team (BitLegion) has push
permissions to these remotes.

### For the frontend

```bash
# add the frontend remote
git remote add heroku-client https://git.heroku.com/resourceium-client.git
# NOTE: deployment is supported only from main and master branches
# push the main branch to be deployed
git subtree push --prefix client heroku-client main

# sometimes, you might need to force push to the heroku remote
# Bash shell
git push heroku-client `git subtree split --prefix client main`:main --force

# Fish shell
git push heroku-client (git subtree split --prefix client main):main --force
```

### For the backend

```bash
# add the backend remote
git remote add heroku-server https://git.heroku.com/resourceium-server.git
# push the main branch to be deployed
git subtree push --prefix server heroku-server main
```
