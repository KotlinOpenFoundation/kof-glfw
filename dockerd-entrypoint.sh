#!/bin/bash
set -e

# Start Xvfb on display :99
Xvfb :99 -screen 0 1024x768x24 >/dev/null 2>&1 &

# Wait for Xvfb to be ready
sleep 1

exec "$@"
